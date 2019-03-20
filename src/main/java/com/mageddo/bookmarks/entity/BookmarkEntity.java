package com.mageddo.bookmarks.entity;

import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;

public class BookmarkEntity {

	private Long id;
	private String name;
	private LocalDateTime lastUpdate;

	public static RowMapper<BookmarkEntity> mapper() {
		return (rs, rowNum) -> new BookmarkEntity()
			;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
