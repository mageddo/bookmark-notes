package com.mageddo.factory;

import com.mageddo.bookmarks.dao.BookmarkDAOSqlite;
import com.mageddo.bookmarks.service.BookmarkService;
import com.mageddo.controller.BookmarkController;

public class ControllerFactory {

	public static BookmarkController getInstance(){
		return new BookmarkController(new BookmarkService(new BookmarkDAOSqlite()));
	}

}
