package com.mageddo.config;

import io.micronaut.core.io.IOUtils;
import io.micronaut.runtime.server.EmbeddedServer;
import io.restassured.RestAssured;

import java.io.*;

import static org.junit.Assert.assertNotNull;

public final class TestUtils {
	private TestUtils() {
	}

	public static void setupRestAssured(EmbeddedServer server) {
		RestAssured.port = server.getPort();
		RestAssured.port = server.getPort();
		RestAssured.baseURI = String.format("%s://%s", server.getScheme(), server.getHost());
	}

	public static String readAsString(String path) {
		try {
			final InputStream resource = TestUtils.class.getResourceAsStream(path);
			assertNotNull("file not found: " + path, resource);
			return IOUtils.readText(new BufferedReader(new InputStreamReader(resource)));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}



}
