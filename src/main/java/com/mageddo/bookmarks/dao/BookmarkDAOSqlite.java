package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
@Requires(env = "sqlite")
public class BookmarkDAOSqlite implements BookmarkDAO {

	private final NamedParameterJdbcTemplate parameterJdbcTemplate;

	public BookmarkDAOSqlite(NamedParameterJdbcTemplate parameterJdbcTemplate) {
		this.parameterJdbcTemplate = parameterJdbcTemplate;
	}

	@Override
	public void createBookmark(BookmarkEntity bookmarkEntity) {
		parameterJdbcTemplate.update("INSERT INTO CUSTOMER_1 VALUES (:v)", Maps.of("v", LocalDateTime.now().toString()));
	}

	@Override
	public List<BookmarkEntity> loadSiteMap() {
		/*
		SELECT * FROM (
			SELECT IDT_BOOKMARK, NAM_BOOKMARK, DAT_UPDATE
			FROM BOOKMARK
			WHERE NUM_VISIBILITY = 1
			AND FLG_DELETED = 0
			AND FLG_ARCHIVED = 0
			ORDER BY IDT_BOOKMARK DESC
		) T LIMIT 100000
		*/
		@RawString final String sql = lateInit();
		return parameterJdbcTemplate.query(sql, BookmarkEntity.mapper());
	}

	@Override
	public List<BookmarkRes> getBookmarks(String tag, int offset, int quantity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<BookmarkRes> getBookmarksByNameOrContent(String query, int offset, int quantity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<BookmarkRes> getBookmarks(int offset, int quantity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int countPublicNotDeleted() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<RecentBookmarksRes> getRecentBookmarks(int pageSize, int startPage) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BookmarkRes findBookmarkRes(long bookmarkId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(BookmarkEntity bookmark) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void associate(long tagId, long bookmarkId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void disassociateTags(long bookmarkId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BookmarkDescriptionRes findBookMarkWithNavigation(int bookmarkId) {
		throw new UnsupportedOperationException();
	}
}
