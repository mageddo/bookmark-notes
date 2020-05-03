package com.mageddo.bookmarks.entity;

import com.mageddo.common.jdbc.JdbcHelper;

import org.springframework.jdbc.core.RowMapper;

public class TagEntity {

  private Integer id;
  private String slug;
  private String name;
  private Integer bookmarkId;

  public static RowMapper<TagEntity> mapper() {
    return (rs, rowNum) -> new TagEntity().setId(rs.getInt("IDT_TAG"))
        .setName(rs.getString("NAM_TAG"))
        .setSlug(rs.getString("COD_SLUG"))
        .setBookmarkId(JdbcHelper.getInteger(rs, "IDT_BOOKMARK"));
  }

  public String getName() {
    return name;
  }

  public TagEntity setName(String name) {
    this.name = name;
    return this;
  }

  public Integer getId() {
    return id;
  }

  public TagEntity setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getSlug() {
    return slug;
  }

  public TagEntity setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public Integer getBookmarkId() {
    return bookmarkId;
  }

  public TagEntity setBookmarkId(Integer bookmarkId) {
    this.bookmarkId = bookmarkId;
    return this;
  }

}
