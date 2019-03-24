package com.mageddo.bookmarks.apiserver.res;

import org.springframework.jdbc.core.RowMapper;

public class TagV1Res {

	private Long id;
	private String name;
	private String slug;

	public Long getId() {
		return id;
	}

	public TagV1Res setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public TagV1Res setName(String name) {
		this.name = name;
		return this;
	}

	public String getSlug() {
		return slug;
	}

	public TagV1Res setSlug(String slug) {
		this.slug = slug;
		return this;
	}

	public static RowMapper<TagV1Res> mapper() {
		return (rs, rowNum) ->
			new TagV1Res()
				.setId(rs.getLong("ID"))
				.setName(rs.getString("NAME"))
				.setSlug(rs.getString("SLUG"))
			;
	}
}
