package com.mageddo.bookmarks.apiserver.res;

import com.mageddo.bookmarks.entity.BookmarkEntity;
import org.springframework.jdbc.core.RowMapper;

public class BookmarkRes {

	private Long id;
	private String name;
	private String link;
	private Integer visibility;
	private String html;
	private Integer length;

	public static RowMapper<BookmarkRes> mapper() {
		return (rs, i) -> {
			final BookmarkRes bookmark = BookmarkRes.valueOf(BookmarkEntity.mapper().mapRow(rs, i));
			bookmark.setLength(rs.getInt("NUM_QUANTITY"));
			return bookmark;
		};
	}

	private static BookmarkRes valueOf(BookmarkEntity bookmark) {
		return new BookmarkRes()
			.setHtml(bookmark.getDescription())
			.setId(bookmark.getId())
			.setName(bookmark.getName())
			.setVisibility(bookmark.getVisibility().getCode())
			.setLink(bookmark.getLink())
		;
	}

	public Long getId() {
		return id;
	}

	public BookmarkRes setId(Long id) {
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
}
