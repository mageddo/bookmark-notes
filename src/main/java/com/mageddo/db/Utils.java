package com.mageddo.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class Utils {

	private Utils() {
	}

	public static Properties loadProps(){
		try {
			Properties properties = new Properties();
			properties.load(getResourceAsStream("/application.properties"));
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static InputStream getResourceAsStream(String resource) {
		InputStream in = Utils.class.getResourceAsStream(resource);
		System.out.printf("m=getResourceAsStream, in=%s, resource=%s%n", resource, Utils.class.getResource(resource));
		return in;
	}
}
