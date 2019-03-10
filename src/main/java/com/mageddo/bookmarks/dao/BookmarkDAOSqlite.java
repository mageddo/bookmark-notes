package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
public class BookmarkDAOSqlite implements BookmarkDAO {

	private final NamedParameterJdbcTemplate parameterJdbcTemplate;

	public BookmarkDAOSqlite(NamedParameterJdbcTemplate parameterJdbcTemplate) {
		this.parameterJdbcTemplate = parameterJdbcTemplate;
	}

	@Override
	public void insert() {
		parameterJdbcTemplate.update("INSERT INTO CUSTOMER_1 VALUES (:v)", Maps.of("v", LocalDateTime.now().toString()));
	}

	@Override
	public List<BookmarkEntity> loadSiteMap() {
		/*
		SELECT * FROM (
			SELECT IDT_BOOKMARK, NAM_BOOKMARK, DAT_UPDATE
			FROM BOOKMARK
			WHERE NUM_VISIBILITY = 1
--			AND FLG_DELETED = 0
			AND FLG_DELETED = false
--			AND FLG_ARCHIVED = 0
			AND FLG_ARCHIVED = false
			ORDER BY IDT_BOOKMARK DESC
		) T LIMIT 100000
		*/
		@RawString
		final String sql = lateInit();
		return parameterJdbcTemplate.query(sql, BookmarkEntity.mapper());
	}
}
