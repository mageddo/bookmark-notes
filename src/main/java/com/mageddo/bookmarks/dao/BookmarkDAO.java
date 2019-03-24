package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;

import java.util.List;

public interface BookmarkDAO {

	void createBookmark(BookmarkEntity bookmarkEntity);

	List<BookmarkEntity> loadSiteMap();

	List<BookmarkRes> getBookmarks(String tag, int offset, int quantity);

	List<BookmarkRes> getBookmarksByNameOrContent(String query, int offset, int quantity);

	List<BookmarkRes> getBookmarks(int offset, int quantity);

	int countPublicNotDeleted();

	List<RecentBookmarksRes> getRecentBookmarks(int pageSize, int startPage);

	BookmarkRes findBookmarkRes(long bookmarkId);

	void update(BookmarkEntity bookmark);
}
