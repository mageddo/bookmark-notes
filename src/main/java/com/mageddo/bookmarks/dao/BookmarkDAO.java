package com.mageddo.bookmarks.dao;

import java.util.List;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;

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

  void associate(long tagId, long bookmarkId);

  void disassociateTags(long bookmarkId);

  BookmarkDescriptionRes findBookMarkWithNavigation(int bookmarkId);

  void deleteBookmark(int bookmarkId);

  void recoverBookmark(int bookmarkId);
}
