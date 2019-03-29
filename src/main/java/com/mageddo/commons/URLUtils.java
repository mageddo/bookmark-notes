package com.mageddo.commons;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class URLUtils {
	private URLUtils() {
	}

	public static String encode(String path) {
		if(path == null){
			return "";
		}
		try {
			return URLEncoder.encode(path, StandardCharsets.UTF_8.displayName()).toLowerCase();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encodeSeoUrl(String path) {
		if(path == null){
			return "";
		}
		return encode(normalizeURL(path));
	}

	static String normalizeURL(String path){
		return StringUtils.stripAccents(path.toLowerCase().replaceAll("\\s", "-"));
	}
}
