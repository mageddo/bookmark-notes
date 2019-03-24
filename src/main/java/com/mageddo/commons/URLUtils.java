package com.mageddo.commons;

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

}
