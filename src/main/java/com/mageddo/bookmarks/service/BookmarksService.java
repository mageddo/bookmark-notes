package com.mageddo.bookmarks.service;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.dao.BookmarkDAO;
import com.mageddo.bookmarks.entity.BookmarkEntity;

import javax.inject.Singleton;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Singleton
public class BookmarksService {

	private static final int SEARCH_MIN_SIZE = 3;
	private final BookmarkDAO bookmarkDAO;

	public BookmarksService(BookmarkDAO bookmarkDAO) {
		this.bookmarkDAO = bookmarkDAO;
	}

	public void createBookmark(BookmarkEntity bookmarkEntity){
		bookmarkDAO.createBookmark(bookmarkEntity);
	}

	public List<BookmarkRes> getBookmarks(String query, String tag, int offset, int quantity){

		tag = tag.trim();
		query = query.trim();

		if (isNotBlank(tag)) {
			return bookmarkDAO.getBookmarks(tag, offset, quantity);
		} else if (isNotBlank(query)) {
			if (query.length() < SEARCH_MIN_SIZE) {
				throw new IllegalArgumentException(String.format("Search text must have %d characters at least", SEARCH_MIN_SIZE));
			}
			return bookmarkDAO.getBookmarksByNameOrContent(query, offset, quantity);
		}
		return bookmarkDAO.getBookmarks(offset, quantity);
	}

	public int countPublicNotDeleted() {
		return bookmarkDAO.countPublicNotDeleted();
	}

	public List<RecentBookmarksRes> getRecentBookmarks(int pageSize, int startPage) {
		return bookmarkDAO.getRecentBookmarks(pageSize, startPage);
	}

	public BookmarkRes getBookmarkRes(long bookmarkId) {
		return bookmarkDAO.findBookmarkRes(bookmarkId);
	}

	public void updateBookmark(BookmarkEntity bookmark) {
		bookmarkDAO.update(bookmark);
	}
}
