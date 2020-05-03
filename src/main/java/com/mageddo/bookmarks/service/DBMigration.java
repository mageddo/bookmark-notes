package com.mageddo.bookmarks.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

import javax.inject.Singleton;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@Singleton
public class DBMigration {

  private final JdbcTemplate sourceJdbcTemplate;
  private final NamedParameterJdbcTemplate targetJdbcTemplate;

  public DBMigration(DataSource sourceDataSource, DataSource targetDataSource) {
    this.sourceJdbcTemplate = new JdbcTemplate(sourceDataSource);
    this.targetJdbcTemplate = new NamedParameterJdbcTemplate(targetDataSource);
  }

  public static void main(String[] args) {

    final HikariConfig sourceConfig = new HikariConfig();
    sourceConfig.setDriverClassName("org.sqlite.JDBC");
    sourceConfig.setJdbcUrl("jdbc:sqlite:/tmp/bookmarks.db");

    final HikariConfig targetConfig = new HikariConfig();
    targetConfig.setDriverClassName("org.postgresql.Driver");
    targetConfig.setJdbcUrl("jdbc:postgresql://postgres:5432/db?currentSchema=bookmarks");
    targetConfig.setUsername("root");
    targetConfig.setPassword("root");
    targetConfig.setSchema("bookmarks");
    targetConfig.setAutoCommit(true);

    new DBMigration(new HikariDataSource(sourceConfig), new HikariDataSource(targetConfig)).migrate();
  }

  public void migrate() {
    migrateBookmarkTable();
    migrateTagTable();
    migrateTagBookmarkTable();
    migrateSystemPropertyTable();
  }

  private void migrateSystemPropertyTable() {
    targetJdbcTemplate.update("DELETE FROM SYSTEM_PROPERTY", new MapSqlParameterSource());
    sourceJdbcTemplate.query("SELECT * FROM SYSTEM_PROPERTY", (rs) -> {
      final StringBuilder sql = new StringBuilder().append("INSERT INTO SYSTEM_PROPERTY ( \n")
          .append("	NAM_PROPERTY, DES_VALUE, DAT_CREATION, \n")
          .append("	DAT_UPDATE \n")
          .append(") VALUES ( \n")
          .append("	:namProperty, :desValue, :datCreation, \n")
          .append("	:datUpdate \n")
          .append(") \n")
          .append(" \n");
      targetJdbcTemplate.update(sql.toString(),
          new MapSqlParameterSource().addValue("namProperty", rs.getString("NAM_PROPERTY"))
              .addValue("desValue", rs.getString("DES_VALUE"))
              .addValue("datCreation", parseTimestamp(rs, "DAT_CREATION"))
              .addValue("datUpdate", parseTimestamp(rs, "DAT_UPDATE"))
      );
    });
  }

  private void migrateTagBookmarkTable() {
    targetJdbcTemplate.update("DELETE FROM TAG_BOOKMARK", new MapSqlParameterSource());
    sourceJdbcTemplate.query("SELECT * FROM TAG_BOOKMARK", (rs) -> {
      final StringBuilder sql = new StringBuilder().append("INSERT INTO TAG_BOOKMARK ( \n")
          .append("	IDT_TAG, IDT_BOOKMARK, DAT_CREATION, \n")
          .append("	DAT_UPDATE \n")
          .append(") VALUES ( \n")
          .append("	:idtTag, :idtBookmark, :datCreation, \n")
          .append("	:datUpdate \n")
          .append(") \n")
          .append(" \n");
      targetJdbcTemplate.update(sql.toString(), new MapSqlParameterSource().addValue("idtTag", rs.getLong("IDT_TAG"))
          .addValue("idtBookmark", rs.getLong("IDT_BOOKMARK"))
          .addValue("datCreation", parseTimestamp(rs, "DAT_CREATION"))
          .addValue("datUpdate", parseTimestamp(rs, "DAT_UPDATE")));
    });
  }

  private void migrateTagTable() {
    targetJdbcTemplate.update("DELETE FROM TAG", new MapSqlParameterSource());
    sourceJdbcTemplate.query("SELECT * FROM TAG", (rs) -> {
      final StringBuilder sql = new StringBuilder().append("INSERT INTO TAG ( \n")
          .append("	IDT_TAG, NAM_TAG, COD_SLUG, \n")
          .append("	DAT_CREATION, DAT_UPDATE \n")
          .append(") VALUES ( \n")
          .append("	:idtTag, :namTag, :codSlug, \n")
          .append("	:datCreation, :datUpdate \n")
          .append(") \n")
          .append(" \n");
      targetJdbcTemplate.update(sql.toString(), new MapSqlParameterSource().addValue("idtTag", rs.getLong("IDT_TAG"))
          .addValue("namTag", rs.getString("NAM_TAG"))
          .addValue("codSlug", rs.getString("COD_SLUG"))
          .addValue("datCreation", parseTimestamp(rs, "DAT_CREATION"))
          .addValue("datUpdate", parseTimestamp(rs, "DAT_UPDATE")));
    });
  }

  private void migrateBookmarkTable() {
    targetJdbcTemplate.update("DELETE FROM BOOKMARK", new MapSqlParameterSource());
    sourceJdbcTemplate.query("SELECT * FROM BOOKMARK", (rs) -> {
      final StringBuilder sql = new StringBuilder().append("INSERT INTO BOOKMARK ( \n")
          .append("	IDT_BOOKMARK, NAM_BOOKMARK, DES_LINK, \n")
          .append("	DES_HTML, FLG_DELETED, FLG_ARCHIVED, \n")
          .append("	NUM_VISIBILITY, DAT_CREATION, DAT_UPDATE \n")
          .append(") VALUES ( \n")
          .append("	:idtBookmark, :namBookmark, :desLink, \n")
          .append("	:desHtml, :flgDeleted, :flgArchived, \n")
          .append("	:numVisibility, :datCreation, :datUpdate \n")
          .append(") \n")
          .append(" \n");
      targetJdbcTemplate.update(sql.toString(),
          new MapSqlParameterSource().addValue("idtBookmark", rs.getLong("IDT_BOOKMARK"))
              .addValue("namBookmark", rs.getString("NAM_BOOKMARK"))
              .addValue("desLink", rs.getString("DES_LINK"))
              .addValue("desHtml", rs.getString("DES_HTML"))
              .addValue("flgDeleted", rs.getBoolean("FLG_DELETED"))
              .addValue("flgArchived", rs.getBoolean("FLG_ARCHIVED"))
              .addValue("numVisibility", rs.getInt("NUM_VISIBILITY"))
              .addValue("datCreation", parseTimestamp(rs, "DAT_CREATION"))
              .addValue("datUpdate", parseTimestamp(rs, "DAT_UPDATE"))
      );
    });
  }

  private Timestamp parseTimestamp(ResultSet rs, String colName) throws SQLException {
    if (rs.getString(colName) == null) {
      return Timestamp.valueOf(LocalDateTime.now());
    }
    return Timestamp.valueOf(LocalDateTime.parse(rs.getString(colName),
        new DateTimeFormatterBuilder().parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter()
    ));
  }

}
