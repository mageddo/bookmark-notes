package com.mageddo.bookmarks.utils;

import com.mageddo.commons.URLUtils;

public final class ThymeleafUtils {

	private ThymeleafUtils() {
	}

	public static ThymeleafUtils getInstance() {
		return new ThymeleafUtils();
	}

	public static String[] splitTags(String tags) {
		return tags == null ? null : tags.split(",");
	}

	public static String encodePath(String path){
		return URLUtils.encode(path);
	}

	public static String encodeSeoUrl(String path){
		return URLUtils.encodeSeoUrl(path);
	}
}
