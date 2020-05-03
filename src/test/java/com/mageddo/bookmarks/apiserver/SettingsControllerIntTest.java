package com.mageddo.bookmarks.apiserver;

import java.io.IOException;

import javax.inject.Inject;

import com.mageddo.common.jackson.JsonUtils;
import com.mageddo.config.DatabaseConfigurator;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import io.micronaut.http.MediaType;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.response.Response;

import static com.mageddo.config.TestUtils.setupRestAssured;
import static com.mageddo.rawstringliterals.RawStrings.lateInit;
import static io.micronaut.http.HttpStatus.NOT_FOUND;
import static io.micronaut.http.HttpStatus.OK;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@Rsl
@MicronautTest(environments = "pg")
class SettingsControllerIntTest {

  private static final JSONComparator comparator = new DefaultComparator(JSONCompareMode.LENIENT);
  @Inject
  private EmbeddedServer server;
  @Inject
  private DatabaseConfigurator databaseConfigurator;

  @BeforeEach
  void before() {
    setupRestAssured(server);
    databaseConfigurator.migrate();
  }

  @Test
  void shouldUpdateExistingSettingProperties() {

    // arrange

		/*
		[{"key":"CODE_BLOCK_MAX_HEIGHT","value":"250"}]
		 */
    @RawString
    final String reqBody = lateInit();

    // act
    final Response res = given().body(reqBody)
        .contentType(MediaType.APPLICATION_JSON)
        .patch("/api/v2.0/settings");

    // assert
    res.then()
        .assertThat()
        .statusCode(OK.getCode())
        .body(emptyOrNullString());

  }

  @Test
  void shouldValidateWhenKeyDoesntExists() {

    // arrange

		/*
		[{"key":"INEXISTENT_KEY","value":"250"}]
		 */
    @RawString
    final String reqBody = lateInit();

    // act
    final Response res = given().body(reqBody)
        .contentType(MediaType.APPLICATION_JSON)
        .patch("/api/v2.0/settings");

    // assert
    res.then()
        .assertThat()
        .statusCode(NOT_FOUND.getCode())
        .body(emptyOrNullString());

  }

  @Test
  void shouldReturnProperty() throws JSONException, IOException {

    // arrange
		/*
		{"key":"CODE_BLOCK_MAX_HEIGHT","value":"250"}
		 */
    @RawString
    final String expectedSettings = lateInit();

    // act
    final Response res = get("/api/v1.0/settings?key=CODE_BLOCK_MAX_HEIGHT");

    // assert
    res.then()
        .assertThat()
        .statusCode(OK.getCode());

    final String body = JsonUtils.instance()
        .readTree(res.andReturn()
            .body()
            .asString())
        .at("/0")
        .toString();
    assertEquals(expectedSettings, body, true);

  }


}
