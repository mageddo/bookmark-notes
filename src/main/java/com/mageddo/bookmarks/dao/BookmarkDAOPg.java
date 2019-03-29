package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
@Requires(env = "pg")
public class BookmarkDAOPg implements BookmarkDAO {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final NamedParameterJdbcTemplate namedJdbcTemplate;

	public BookmarkDAOPg(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	@Override
	public void createBookmark(BookmarkEntity bookmarkEntity) {
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
		final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedJdbcTemplate.update(
			sql, new MapSqlParameterSource()
			.addValue("name", bookmarkEntity.getName())
			.addValue("link", bookmarkEntity.getLink())
			.addValue("desc", bookmarkEntity.getDescription())
			.addValue("deleted", bookmarkEntity.isDeleted())
			.addValue("archived", bookmarkEntity.isArchived())
			.addValue("visibility", bookmarkEntity.getVisibility().getCode())
			.addValue("updated", Timestamp.valueOf(bookmarkEntity.getLastUpdate()))
			, keyHolder, new String[]{"IDT_BOOKMARK".toLowerCase()}
		);
		bookmarkEntity.setId(keyHolder.getKey().intValue());
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
		return namedJdbcTemplate.query(sql, BookmarkEntity.mapper());
	}

	@Override
	public List<BookmarkRes> getBookmarks(String tag, int offset, int quantity) {
		/*
		WITH FILTER AS (
			SELECT DISTINCT B.* FROM TAG_BOOKMARK TB
				INNER JOIN BOOKMARK B ON B.IDT_BOOKMARK = TB.IDT_BOOKMARK
				WHERE IDT_TAG IN (
					SELECT T.IDT_TAG FROM TAG T
						WHERE T.COD_SLUG = :slug
				)
				AND B.FLG_DELETED = FALSE
		)
		SELECT
			IDT_BOOKMARK, NAM_BOOKMARK, DES_LINK, FLG_ARCHIVED, FLG_DELETED, DAT_UPDATE,
			DAT_CREATION, NUM_VISIBILITY,
			(SELECT COUNT(IDT_BOOKMARK) FROM FILTER) AS NUM_QUANTITY, SUBSTR(DES_HTML, 0, 160) DES_HTML
		FROM FILTER OFFSET :offset LIMIT :limit
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(
			sql,
			Maps.of("slug", tag, "offset", offset, "limit", quantity),
			BookmarkRes.mapper()
		);
	}

	@Override
	public List<BookmarkRes> getBookmarksByNameOrContent(String query, int offset, int quantity) {
		logger.debug("status=begin, query={}, offset={}, quantity={}", query, offset, quantity);

		/*
			WITH FILTER AS (
				SELECT
					*
				FROM BOOKMARK B
				WHERE FLG_DELETED=FALSE
				AND ( NAM_BOOKMARK ILIKE :query OR DES_HTML ILIKE :query )
				ORDER BY DAT_UPDATE DESC
			)
			SELECT
				IDT_BOOKMARK, NAM_BOOKMARK, FLG_ARCHIVED, FLG_DELETED, DAT_CREATION, DAT_UPDATE, DES_LINK, NUM_VISIBILITY,
				(SELECT COUNT(IDT_BOOKMARK) FROM FILTER) AS NUM_QUANTITY, SUBSTR(DES_HTML, 0, 160) AS DES_HTML
			FROM FILTER OFFSET :offset LIMIT :limit
		 */
		@RawString
		final String sql = lateInit();

		final List<BookmarkRes> bookmarks = namedJdbcTemplate.query(
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
				ORDER BY DAT_UPDATE DESC
			)
			SELECT
				IDT_BOOKMARK, NAM_BOOKMARK, FLG_ARCHIVED, NUM_VISIBILITY,
				FLG_DELETED, DAT_UPDATE, DES_LINK, DAT_CREATION,
				SUBSTR(DES_HTML, 0, 160) DES_HTML, (SELECT COUNT(IDT_BOOKMARK) FROM LIST) NUM_QUANTITY
			FROM LIST WHERE FLG_DELETED=FALSE OFFSET :offset LIMIT :quantity
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(
			sql,
			new MapSqlParameterSource()
				.addValue("offset", offset)
				.addValue("quantity", quantity)
			, BookmarkRes.mapper()
		);
	}

	@Override
	public int countPublicNotDeleted() {
		/*
			SELECT COUNT(1) AS COUNT FROM BOOKMARK B
			WHERE B.FLG_DELETED = FALSE AND B.NUM_VISIBILITY = 1
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.queryForObject(sql, Maps.of(), Integer.class);
	}

	@Override
	public List<RecentBookmarksRes> getRecentBookmarks(int pageSize, int startPage) {
		/*
			SELECT
				B.IDT_BOOKMARK AS ID, B.NAM_BOOKMARK AS NAME, STRING_AGG(T.NAM_TAG, ',') AS TAGS
			FROM BOOKMARK B
			LEFT JOIN TAG_BOOKMARK TB ON TB.IDT_BOOKMARK = B.IDT_BOOKMARK
			LEFT JOIN TAG T ON T.IDT_TAG = TB.IDT_TAG
			WHERE B.FLG_DELETED = FALSE
			AND B.NUM_VISIBILITY = 1
			GROUP BY B.IDT_BOOKMARK
			ORDER BY B.IDT_BOOKMARK DESC
			OFFSET :offset LIMIT :limit
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(
			sql, Maps.of("offset", startPage, "limit", pageSize), RecentBookmarksRes.mapper()
		);
	}

	@Override
	public BookmarkRes findBookmarkRes(long bookmarkId) {
		/*
		SELECT
			IDT_BOOKMARK, NAM_BOOKMARK, DES_LINK, DES_HTML, FLG_DELETED, FLG_ARCHIVED, DAT_CREATION,
			-1 AS NUM_QUANTITY, NUM_VISIBILITY, DAT_UPDATE
		FROM BOOKMARK WHERE IDT_BOOKMARK = :id
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.queryForObject(sql, Maps.of("id", bookmarkId), BookmarkRes.mapper());

	}

	@Override
	public void update(BookmarkEntity bookmark) {
		/*
			UPDATE BOOKMARK SET
				NAM_BOOKMARK=:name, DES_LINK=:link, DES_HTML=:html,
				NUM_VISIBILITY=:visibility, DAT_UPDATE=CURRENT_TIMESTAMP
			WHERE IDT_BOOKMARK=:id
		 */
		@RawString
		final String sql = lateInit();
		final int affected = namedJdbcTemplate.update(
			sql, new MapSqlParameterSource()
				.addValue("name", bookmark.getName())
				.addValue("link", bookmark.getLink())
				.addValue("html", bookmark.getDescription())
				.addValue("visibility", bookmark.getVisibility().getCode())
				.addValue("id", bookmark.getId())
		);
		Validate.isTrue(affected == 1, "Should update exactly one bookmark ", affected, bookmark.getId(), bookmark.getName());
	}

	@Override
	public void associate(long tagId, long bookmarkId) {
		/*
			INSERT INTO TAG_BOOKMARK (IDT_TAG, IDT_BOOKMARK, DAT_UPDATE) VALUES (:id, :bookmarkId, TIMEZONE('UTC', NOW()))
		 */
		@RawString
		final String sql = lateInit();
		namedJdbcTemplate.update(sql, Maps.of("id", tagId, "bookmarkId", bookmarkId));
	}

	@Override
	public void disassociateTags(long bookmarkId) {
		namedJdbcTemplate.update(
			"DELETE FROM TAG_BOOKMARK WHERE IDT_BOOKMARK = :bookmarkId",
			Maps.of("bookmarkId", bookmarkId)
		);
	}

	@Override
	public BookmarkDescriptionRes findBookMarkWithNavigation(int bookmarkId) {
		/*
		SELECT
			IDT_BOOKMARK, NAM_BOOKMARK, NUM_VISIBILITY, DES_HTML, DES_LINK,
			DAT_CREATION, DAT_UPDATE, FLG_ARCHIVED, FLG_DELETED, NULL::INTEGER AS NUM_QUANTITY
		FROM BOOKMARK
		WHERE IDT_BOOKMARK IN(
				(SELECT MAX(IDT_BOOKMARK) FROM BOOKMARK WHERE IDT_BOOKMARK < :id AND FLG_DELETED = FALSE AND NUM_VISIBILITY = 1),
				:id,
				(SELECT MIN(IDT_BOOKMARK) FROM BOOKMARK WHERE IDT_BOOKMARK > :id AND FLG_DELETED = FALSE AND NUM_VISIBILITY = 1)
		)
		AND FLG_DELETED = FALSE AND NUM_VISIBILITY = 1
		 */
		@RawString
		final String sql = lateInit();
		return new BookmarkDescriptionRes(
			namedJdbcTemplate.query(sql, Maps.of("id", bookmarkId), BookmarkRes.mapper()),
			bookmarkId
		);
	}

	@Override
	public void deleteBookmark(int bookmarkId) {
		final int affected = namedJdbcTemplate.update(
			"UPDATE BOOKMARK SET FLG_DELETED=TRUE WHERE IDT_BOOKMARK=:id", Maps.of("id", bookmarkId)
		);
		Validate.isTrue(affected == 1);
	}

	@Override
	public void recoverBookmark(int bookmarkId) {
		final int affected = namedJdbcTemplate.update(
			"UPDATE BOOKMARK SET FLG_DELETED=FALSE WHERE IDT_BOOKMARK=:id", Maps.of("id", bookmarkId)
		);
		Validate.isTrue(affected == 1);
	}
}
