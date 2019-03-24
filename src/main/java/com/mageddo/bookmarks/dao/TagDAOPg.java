package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.context.annotation.Requires;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Singleton;
import java.util.List;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;
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
		final String sql = lateInit();
		return namedJdbcTemplate.query(sql, mapOf(), TagV1Res.mapper());
	}

	@Override
	public List<TagEntity> findTags(long bookmarkId) {
		/*
		SELECT
			T.IDT_TAG, T.NAM_TAG, T.COD_SLUG, TB.IDT_BOOKMARK
		FROM TAG T
		LEFT JOIN TAG_BOOKMARK TB
		ON T.IDT_TAG = TB.IDT_TAG
		WHERE tb.idt_bookmark = :bookmarkId
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(sql, Maps.of("bookmarkId", bookmarkId), TagEntity.mapper());
	}
}
