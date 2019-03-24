package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.RawStrings;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Singleton;
import java.util.List;

import static io.micronaut.core.util.CollectionUtils.mapOf;

@Rsl
@Singleton
@Requires(env = "pg")
public class TagDAOPg implements TagDAO {

	private final NamedParameterJdbcTemplate namedJdbcTemplate;

	public TagDAOPg(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	@Override
	public List<TagV1Res> findTags(){
		/*
			SELECT
				IDT_TAG AS ID, NAM_TAG AS NAME, COD_SLUG AS SLUG
			FROM TAG ORDER BY NAM_TAG
		 */
		@RawString
		final String sql = RawStrings.lateInit();
		return namedJdbcTemplate.query(sql, mapOf(), TagV1Res.mapper());
	}
}
