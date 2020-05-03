package com.mageddo.bookmarks.apiserver.res;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.jackson.TruncatedLocalDateTimeConverter;

import org.springframework.jdbc.core.RowMapper;

import static com.mageddo.bookmarks.enums.BookmarkVisibility.PRIVATE;
import static com.mageddo.bookmarks.enums.BookmarkVisibility.mustFromCode;

public class BookmarkRes {

  private Integer id;
  private String name;
  private String link;
  private Integer visibility;
  private String html;
  private Integer length;
  private List<String> tags;
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;

  public static RowMapper<BookmarkRes> mapper() {
    return (rs, i) -> {
      final BookmarkRes bookmark = BookmarkRes.valueOf(BookmarkEntity.mapper()
          .mapRow(rs, i));
      bookmark.setLength(rs.getInt("NUM_QUANTITY"));
      return bookmark;
    };
  }

  private static BookmarkRes valueOf(BookmarkEntity bookmark) {
    return new BookmarkRes().setHtml(bookmark.getDescription())
        .setId(bookmark.getId())
        .setName(bookmark.getName())
        .setVisibility(bookmark.getVisibility()
            .getCode())
        .setLink(bookmark.getLink())
        .setUpdateDate(bookmark.getLastUpdate())
        .setCreationDate(bookmark.getCreation());
  }

  @JsonSerialize(using = TruncatedLocalDateTimeConverter.Serializer.class)
  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public BookmarkRes setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  @JsonSerialize(using = TruncatedLocalDateTimeConverter.Serializer.class)
  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public BookmarkRes setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
    return this;
  }

  public Integer getId() {
    return id;
  }

  public BookmarkRes setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public BookmarkRes setName(String name) {
    this.name = name;
    return this;
  }

  public Integer getVisibility() {
    return visibility;
  }

  public BookmarkRes setVisibility(Integer visibility) {
    this.visibility = visibility;
    return this;
  }

  public String getHtml() {
    return html;
  }

  public BookmarkRes setHtml(String html) {
    this.html = html;
    return this;
  }

  public Integer getLength() {
    return length;
  }

  public BookmarkRes setLength(Integer length) {
    this.length = length;
    return this;
  }

  public String getLink() {
    return link;
  }

  public BookmarkRes setLink(String link) {
    this.link = link;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public BookmarkRes setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public BookmarkEntity toBookmark() {
    return new BookmarkEntity().setDescription(getHtml())
        .setVisibility(getVisibility() == null ? PRIVATE : mustFromCode(getVisibility()))
        .setName(getName())
        .setDeleted(false)
        .setArchived(false)
        .setLink(getLink())
        .setLastUpdate(LocalDateTime.now())
        .setId(getId());
  }
}
