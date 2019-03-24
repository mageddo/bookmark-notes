package com.mageddo.bookmarks.entity;

import com.mageddo.common.jdbc.JdbcHelper;
import org.springframework.jdbc.core.RowMapper;

public class TagEntity {

	private Long id;
	private String slug;
	private String name;
	private Long bookmarkId;

	public String getName() {
		return name;
	}

	public TagEntity setName(String name) {
		this.name = name;
		return this;
	}

	public Long getId() {
		return id;
	}

	public TagEntity setId(Long id) {
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

	public Long getBookmarkId() {
		return bookmarkId;
	}

	public TagEntity setBookmarkId(Long bookmarkId) {
		this.bookmarkId = bookmarkId;
		return this;
	}

	public static RowMapper<TagEntity> mapper() {
		return (rs, rowNum) -> new TagEntity()
			.setId(rs.getLong("IDT_TAG"))
			.setName(rs.getString("NAM_TAG"))
			.setSlug(rs.getString("COD_SLUG"))
			.setBookmarkId(JdbcHelper.getLong(rs, "IDT_BOOKMARK"))
		;
	}

}
