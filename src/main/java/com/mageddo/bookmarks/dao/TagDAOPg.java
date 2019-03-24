package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.utils.Tags;
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
		WHERE TB.IDT_BOOKMARK = :bookmarkId
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(sql, Maps.of("bookmarkId", bookmarkId), TagEntity.mapper());
	}

	@Override
	public List<TagEntity> findTags(String query) {
		/*
			SELECT
				T.IDT_TAG, T.NAM_TAG, T.COD_SLUG, NULL::BIGINT AS IDT_BOOKMARK
			FROM TAG T WHERE NAM_TAG LIKE :query
		 */
		@RawString
		final String sql = lateInit();
		return namedJdbcTemplate.query(
			sql, Maps.of("query", String.format("%%%s%%", query)), TagEntity.mapper()
		);
	}

	@Override
	public void createIgnoreDuplicates(Tags.Tag tag) {
		/*
		INSERT INTO TAG (
			NAM_TAG, COD_SLUG,
			DAT_CREATION, DAT_UPDATE
		) VALUES (
			:name, :slug,
			TIMEZONE('UTC', NOW()), TIMEZONE('UTC', NOW())
		)
		ON CONFLICT DO NOTHING
		 */
		@RawString
		final String sql = lateInit();
		namedJdbcTemplate.update(sql, Maps.of("name", tag.getName(), "slug", tag.getSlug()));
	}

	@Override
	public TagEntity findTag(String slug) {
		return namedJdbcTemplate.queryForObject(
			"SELECT T.*, NULL::BIGINT AS IDT_BOOKMARK FROM TAG T WHERE COD_SLUG=:slug",
			Maps.of("slug", slug),
			TagEntity.mapper()
		);
	}
}
