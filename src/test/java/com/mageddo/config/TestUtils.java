package com.mageddo.config;

import io.micronaut.core.io.IOUtils;
import io.micronaut.runtime.server.EmbeddedServer;
import io.restassured.RestAssured;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;

public final class TestUtils {
	private TestUtils() {
	}

	public static void setupRestAssured(EmbeddedServer server) {
		RestAssured.port = server.getPort();
		RestAssured.port = server.getPort();
		RestAssured.baseURI = String.format("%s://%s", server.getScheme(), server.getHost());
	}

	public static String readAsString(String path) throws Exception {
		final InputStream resource = TestUtils.class.getResourceAsStream(path);
		assertNotNull("file not found: " + path, resource);
		return IOUtils.readText(new BufferedReader(new InputStreamReader(resource)));
	}
}
