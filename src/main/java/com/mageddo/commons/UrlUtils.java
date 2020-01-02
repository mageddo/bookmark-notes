package com.mageddo.commons;

import io.micronaut.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class UrlUtils {
	private UrlUtils() {
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

	public static String getHost(HttpRequest req) {
		return req.getHeaders().get("Host");
	}
	public static String getFullHost(HttpRequest req) {
		return String.format("http://%s", getHost(req));
	}

	static String normalizeURL(String path){
		return StringUtils.stripAccents(path.toLowerCase().replaceAll("\\s", "-"));
	}
}
