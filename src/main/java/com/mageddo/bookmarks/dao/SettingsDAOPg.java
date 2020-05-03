package com.mageddo.bookmarks.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Singleton;

import com.mageddo.bookmarks.entity.SettingEntity;
import com.mageddo.commons.Maps;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import io.micronaut.context.annotation.Requires;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
@Requires("pg")
public class SettingsDAOPg implements SettingsDAO {

  private final NamedParameterJdbcTemplate namedJdbcTemplate;

  public SettingsDAOPg(NamedParameterJdbcTemplate namedJdbcTemplate) {
    this.namedJdbcTemplate = namedJdbcTemplate;
  }

  @Override
  public List<SettingEntity> findAll() {
		/*
		SELECT
			NAM_PROPERTY, DES_VALUE, DAT_UPDATE
		FROM SYSTEM_PROPERTY
		 */
    @RawString
    final String sql = lateInit();
    return namedJdbcTemplate.query(sql, SettingEntity.mapper());
  }

  @Override
  public SettingEntity find(String key) {
		/*
		SELECT
			NAM_PROPERTY, DES_VALUE, DAT_UPDATE
		FROM SYSTEM_PROPERTY
		WHERE NAM_PROPERTY = :name
		 */
    @RawString
    final String sql = lateInit();
    try {
      return namedJdbcTemplate.queryForObject(sql, Maps.of("name", key), SettingEntity.mapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void patch(SettingEntity settingEntity) {

		/*
		UPDATE SYSTEM_PROPERTY SET
			DAT_UPDATE = :update,
			DES_VALUE = :value
		WHERE NAM_PROPERTY = :name
		 */
    @RawString
    final String sql = lateInit();

    namedJdbcTemplate.update(sql, new MapSqlParameterSource().addValue("update", Timestamp.valueOf(LocalDateTime.now()))
        .addValue("value", settingEntity.getValue())
        .addValue("name", settingEntity.getKey()));
  }
}
