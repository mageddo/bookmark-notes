package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.controller.res.BookmarkRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
@Requires(env = "pg")
public class BookmarkDAOPg implements BookmarkDAO {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final NamedParameterJdbcTemplate parameterJdbcTemplate;

	public BookmarkDAOPg(NamedParameterJdbcTemplate parameterJdbcTemplate) {
		this.parameterJdbcTemplate = parameterJdbcTemplate;
	}

	@Override
	public void saveBookmark(BookmarkEntity bookmarkEntity) {
		/*
			INSERT INTO BOOKMARK (
				NAM_BOOKMARK, DES_LINK,
				DES_HTML, FLG_DELETED,
				FLG_ARCHIVED, NUM_VISIBILITY, DAT_UPDATE
			) VALUES (
				:name, :link,
				:desc, :deleted,
				:archived, :visibility,
				:updated
			)
		 */
		@RawString
		final String sql = lateInit();
		parameterJdbcTemplate.update(
			sql, new MapSqlParameterSource()
			.addValue("name", bookmarkEntity.getName())
			.addValue("link", bookmarkEntity.getLink())
			.addValue("desc", bookmarkEntity.getDescription())
			.addValue("deleted", bookmarkEntity.isDeleted())
			.addValue("archived", bookmarkEntity.isArchived())
			.addValue("visibility", bookmarkEntity.getVisibility().getCode())
			.addValue("updated", Timestamp.valueOf(bookmarkEntity.getLastUpdate()))
		);
	}

	@Override
	public List<BookmarkEntity> loadSiteMap() {
		/*
		SELECT * FROM (
			SELECT *
			FROM BOOKMARK
			WHERE NUM_VISIBILITY = 1
			AND FLG_DELETED = false
			AND FLG_ARCHIVED = false
			ORDER BY IDT_BOOKMARK DESC
		) T LIMIT 100000
		*/
		@RawString
		final String sql = lateInit();
		return parameterJdbcTemplate.query(sql, BookmarkEntity.mapper());
	}

	@Override
	public List<BookmarkRes> getBookmarks(String tag, int offset, int quantity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<BookmarkRes> getBookmarksByNameOrContent(String query, int offset, int quantity) {
		logger.debug("status=begin, query={}, offset={}, quantity={}", query, offset, quantity);

		/*
			WITH FILTER AS (
				SELECT * FROM BOOKMARK B
					WHERE FLG_DELETED=FALSE
					AND ( NAM_BOOKMARK LIKE :query OR DES_HTML LIKE :query )
			)
			SELECT
				IDT_BOOKMARK, NAM_BOOKMARK, FLG_ARCHIVED, FLG_DELETED, DAT_UPDATE, DES_LINK, NUM_VISIBILITY,
				(SELECT COUNT(IDT_BOOKMARK) FROM FILTER) AS NUM_QUANTITY, SUBSTR(DES_HTML, 0, 160) AS DES_HTML
			FROM FILTER OFFSET :offset LIMIT :limit
		 */
		@RawString
		final String sql = lateInit();

		final List<BookmarkRes> bookmarks = parameterJdbcTemplate.query(
			sql,
			new MapSqlParameterSource()
			.addValue("query", String.format("%%%s%%", query))
			.addValue("offset", offset)
			.addValue("limit", quantity)
			, BookmarkRes.mapper()
		);
		logger.debug("status=success, size={}, query={}", bookmarks.size(), query);
		return bookmarks;
	}

	@Override
	public List<BookmarkRes> getBookmarks(int offset, int quantity) {
		/*
			WITH LIST AS (
				SELECT * FROM BOOKMARK
			)
			SELECT
				IDT_BOOKMARK, NAM_BOOKMARK, FLG_ARCHIVED, NUM_VISIBILITY,
				FLG_DELETED, DAT_UPDATE, DES_LINK,
				SUBSTR(DES_HTML, 0, 160) DES_HTML, (SELECT COUNT(IDT_BOOKMARK) FROM LIST) NUM_QUANTITY
			FROM LIST WHERE FLG_DELETED=FALSE OFFSET :offset LIMIT :quantity
		 */
		@RawString
		final String sql = lateInit();
		return parameterJdbcTemplate.query(
			sql,
			new MapSqlParameterSource()
				.addValue("offset", offset)
				.addValue("quantity", quantity)
			, BookmarkRes.mapper()
		);
	}
}
