package com.mageddo.bookmarks.entity;

import com.mageddo.bookmarks.enums.BookmarkVisibility;
import com.mageddo.common.jdbc.JdbcHelper;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;

public class BookmarkEntity {

	private Long id;
	private String name;
	private LocalDateTime lastUpdate;
	private String link;
	private String description;
	private boolean deleted;
	private boolean archived;
	private BookmarkVisibility visibility;

	public BookmarkEntity() {
	}

	public BookmarkEntity(String name, BookmarkVisibility visibility) {
		this.name = name;
		this.visibility = visibility;
	}

	public static RowMapper<BookmarkEntity> mapper() {
		return (rs, rowNum) -> new BookmarkEntity()
			.setArchived(rs.getBoolean("flg_archived"))
			.setDeleted(rs.getBoolean("flg_deleted"))
			.setDescription(rs.getString("des_html"))
			.setId(rs.getLong("idt_bookmark"))
			.setLastUpdate(JdbcHelper.getLocalDateTime(rs, "dat_update"))
			.setLink(rs.getString("des_link"))
			.setName(rs.getString("nam_bookmark"))
			.setVisibility(BookmarkVisibility.mustFromCode(rs.getInt("num_visibility")))
			;
	}

	public Long getId() {
		return id;
	}

	public BookmarkEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public BookmarkEntity setName(String name) {
		this.name = name;
		return this;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public BookmarkEntity setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public String getLink() {
		return link;
	}

	public BookmarkEntity setLink(String link) {
		this.link = link;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public BookmarkEntity setDescription(String description) {
		this.description = description;
		return this;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public BookmarkEntity setDeleted(boolean deleted) {
		this.deleted = deleted;
		return this;
	}

	public boolean isArchived() {
		return archived;
	}

	public BookmarkEntity setArchived(boolean archived) {
		this.archived = archived;
		return this;
	}

	public BookmarkVisibility getVisibility() {
		return visibility;
	}

	public BookmarkEntity setVisibility(BookmarkVisibility visibility) {
		this.visibility = visibility;
		return this;
	}
}
