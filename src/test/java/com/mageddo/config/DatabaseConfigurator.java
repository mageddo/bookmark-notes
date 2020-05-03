package com.mageddo.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import com.mageddo.commons.Maps;
import com.mageddo.commons.MigrationUtils;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import io.micronaut.context.env.Environment;

@Singleton
public class DatabaseConfigurator {

  private static boolean migrated = false;

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final NamedParameterJdbcTemplate namedJdbcTemplate;
  private final PlatformTransactionManager platformTransactionManager;
  private final Environment environment;

  public DatabaseConfigurator(NamedParameterJdbcTemplate namedJdbcTemplate,
      PlatformTransactionManager platformTransactionManager, Environment environment) {
    this.namedJdbcTemplate = namedJdbcTemplate;
    this.platformTransactionManager = platformTransactionManager;
    this.environment = environment;
  }

  public void migrate() {
    if (!migrated) {
      final Flyway flyway = MigrationUtils.getFlyway(environment);
      flyway.clean();
      flyway.migrate();
      migrated = true;
    }
    new TransactionTemplate(platformTransactionManager).execute((st) -> {
      logger.info("status=schema-truncating");
      final StringBuilder sql = new StringBuilder().append("SELECT  \n")
          .append("	CONCAT(TABLE_SCHEMA, '.', TABLE_NAME) \n")
          .append("FROM INFORMATION_SCHEMA.TABLES \n")
          .append("WHERE TABLE_SCHEMA = CURRENT_SCHEMA() \n")
          .append("AND TABLE_NAME NOT IN (:tables) \n")
          .append("ORDER BY TABLE_NAME \n");
      final List<String> tables = namedJdbcTemplate.query(sql.toString(), Maps.of("tables", skipTables()),
          (rs, i) -> rs.getString(1)
      );
      namedJdbcTemplate.update("SET CONSTRAINTS ALL DEFERRED", Maps.of());
      for (final String table : tables) {
        namedJdbcTemplate.update("DELETE FROM " + table, Maps.of());
      }
      namedJdbcTemplate.update("SET CONSTRAINTS ALL IMMEDIATE", Maps.of());
      logger.info("status=schema-truncated");
      try {
        namedJdbcTemplate.update(TestUtils.readAsString("/db/base-data.sql"), Maps.of());
      } catch (Exception e) {
        logger.error("status=cant-run-base-data", e);
        throw new RuntimeException(e);
      }
      logger.info("status=base-data-executed");
      return null;
    });
  }

  public Collection<String> skipTables() {
    return Arrays.asList("flyway_schema_history".toLowerCase(), "system_property".toLowerCase());
  }

}
