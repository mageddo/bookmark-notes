package com.mageddo.bookmarks.apiserver.res;

import org.springframework.jdbc.core.RowMapper;

public class RecentBookmarksRes {

	private Long id;
	private String name;
	private String tags;

	public Long getId() {
		return id;
	}

	public RecentBookmarksRes setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public RecentBookmarksRes setName(String name) {
		this.name = name;
		return this;
	}

	public String getTags() {
		return tags;
	}

	public RecentBookmarksRes setTags(String tags) {
		this.tags = tags;
		return this;
	}

	public static RowMapper<RecentBookmarksRes> mapper() {
		return (rs, rowNum) ->
			new RecentBookmarksRes()
				.setId(rs.getLong("ID"))
				.setName(rs.getString("NAME"))
				.setTags(rs.getString("TAGS"))
		;
	}
}
