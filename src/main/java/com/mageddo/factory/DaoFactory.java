package com.mageddo.factory;

import com.mageddo.bookmarks.dao.BookmarkDAO;
import com.mageddo.bookmarks.dao.BookmarkDAOSqlite;
import com.mageddo.commons.Maps;

import java.util.Map;

public final class DaoFactory {
	private DaoFactory() {
	}

	public <T>T getInstance(Class<T> clazz){
		return (T) sqliteDaos().get(clazz);
	}

	public Map<Class, Object> sqliteDaos(){
		return Maps.of(
			BookmarkDAO.class, new BookmarkDAOSqlite()
		);
	}

}
