package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.entity.BookmarkEntity;

import java.util.List;

public interface BookmarkDAO {
	List<BookmarkEntity> loadSiteMap();
}
