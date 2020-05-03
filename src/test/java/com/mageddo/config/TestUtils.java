package com.mageddo.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

import io.micronaut.core.io.IOUtils;
import io.micronaut.runtime.server.EmbeddedServer;
import io.restassured.RestAssured;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
      assertNotNull(resource, "file not found: " + path);
      return IOUtils.readText(new BufferedReader(new InputStreamReader(resource)));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }


}
