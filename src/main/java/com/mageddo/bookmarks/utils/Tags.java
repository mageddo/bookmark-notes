package com.mageddo.bookmarks.utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Tags {
	private Tags() {
	}

	public static Set<Tag> toTags(List<String> rawTags){
		if(rawTags == null){
			return Collections.emptySet();
		}
		return rawTags
			.stream()
			.map(Tag::new)
			.filter(it -> it.getSlug().length() > 0)
			.collect(Collectors.toSet());
	}

	static String toSlug(String name){
		return name
			.toLowerCase()
			.replace("/[^a-z0-9\\ \\-_]+/g", "")
			.trim()
			.replace("/[\\ _]+/g", "\\-");
	}


	public static class Tag {

		private final String name;
		private final String slug;

		public Tag(String name) {
			this.name = name.trim();
			this.slug = toSlug(name);
		}

		public String getName() {
			return name;
		}

		public String getSlug() {
			return slug;
		}
	}
}
