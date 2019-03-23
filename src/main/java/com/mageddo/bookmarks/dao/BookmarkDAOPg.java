package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
@Requires(env = "pg")
public class BookmarkDAOPg implements BookmarkDAO {

	private final NamedParameterJdbcTemplate parameterJdbcTemplate;

	public BookmarkDAOPg(NamedParameterJdbcTemplate parameterJdbcTemplate) {
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
			AND FLG_DELETED = false
			AND FLG_ARCHIVED = false
			ORDER BY IDT_BOOKMARK DESC
		) T LIMIT 100000
		*/
		@RawString
		final String sql = lateInit();
		return parameterJdbcTemplate.query(sql, BookmarkEntity.mapper());
	}
}
