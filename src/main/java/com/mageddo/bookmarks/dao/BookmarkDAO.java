package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.controller.res.BookmarkRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;

import java.util.List;

public interface BookmarkDAO {

	void saveBookmark(BookmarkEntity bookmarkEntity);

	List<BookmarkEntity> loadSiteMap();

	List<BookmarkRes> getBookmarks(String tag, int offset, int quantity);

	List<BookmarkRes> getBookmarksByNameOrContent(String query, int offset, int quantity);

	List<BookmarkRes> getBookmarks(int offset, int quantity);
}
