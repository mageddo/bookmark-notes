package com.mageddo.bookmarks.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mageddo.common.jdbc.JdbcHelper;

import org.springframework.jdbc.core.RowMapper;

public class SettingEntity {

  private String key;
  private String value;

  @JsonIgnore
  private LocalDateTime update;

  public static RowMapper<SettingEntity> mapper() {
    return (rs, rowNum) -> {
      return new SettingEntity().setUpdate(JdbcHelper.getLocalDateTime(rs, "DAT_UPDATE"))
          .setValue(rs.getString("DES_VALUE"))
          .setKey(rs.getString("NAM_PROPERTY"));
    };
  }

  public String getValue() {
    return value;
  }

  public SettingEntity setValue(String value) {
    this.value = value;
    return this;
  }

  public String getKey() {
    return key;
  }

  public SettingEntity setKey(String key) {
    this.key = key;
    return this;
  }

  public LocalDateTime getUpdate() {
    return update;
  }

  public SettingEntity setUpdate(LocalDateTime update) {
    this.update = update;
    return this;
  }

  public enum Setting {
    CODE_BLOCK_MAX_HEIGHT, //
    MOBILE_CODE_BLOCK_MAX_HEIGHT, //
    CODE_STYLE_TAB_SIZE, //
    CODE_STYLE_TAB_STYLE, //
    ;
  }
}
