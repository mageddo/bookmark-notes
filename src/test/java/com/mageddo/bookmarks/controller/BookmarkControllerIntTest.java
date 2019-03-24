package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.config.DatabaseConfigurator;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.mageddo.bookmarks.enums.BookmarkVisibility.PRIVATE;
import static com.mageddo.bookmarks.enums.BookmarkVisibility.PUBLIC;
import static com.mageddo.config.HamcrestUtils.jsonMatchingPattern;
import static com.mageddo.config.TestUtils.setupRestAssured;
import static com.mageddo.rawstringliterals.RawStrings.lateInit;
import static io.micronaut.http.HttpStatus.OK;
import static io.restassured.RestAssured.get;

@Rsl
@MicronautTest(environments = "pg")
class BookmarkControllerIntTest {

	@Inject
	private EmbeddedServer server;

	@Inject
	private DatabaseConfigurator databaseConfigurator;

	@Inject
	private BookmarksService bookmarksService;

	@BeforeEach
	void before(){
		setupRestAssured(server);
		databaseConfigurator.migrate();
	}

	@Test
	void shouldValidateQuantity() {

		// arrange
		/*
		{"code":400,"message":"Please pass a valid quantity"}
		 */
		@RawString
		final String expectedValidationMsg  = lateInit();

		// act // assert

		get("/api/v1.0/bookmark")
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST.getCode())
			.body(jsonMatchingPattern(expectedValidationMsg))
		;

	}

	@Test
	void GetV1_0__shouldListBookmarksAndLimitQuantityToOne() throws JSONException {

		// arrange

		/*
		[{"id":1,"name":"X","visibility":1,"length":3}]
		 */
		@RawString
		final String expectedBookmarks = lateInit();

		bookmarksService.saveBookmark(
			new BookmarkEntity()
			.setName("X")
			.setVisibility(PUBLIC)
		);

		bookmarksService.saveBookmark(new BookmarkEntity()
			.setName("X2")
			.setVisibility(PUBLIC)
		);

		bookmarksService.saveBookmark(
			new BookmarkEntity()
			.setName("X3")
			.setVisibility(PRIVATE)
		);

		// act
		final Response res = get("/api/v1.0/bookmark?from=0&quantity=1");

		// assert
		res
			.then()
			.assertThat()
			.statusCode(OK.getCode())
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(jsonMatchingPattern(expectedBookmarks, "[0].id", "\\d+"))
		;
	}


	@Test
	void getV1_0ListBookmarksValidateFromSuccess(){

		// arrange

		/*
		[
			{"id":2,"name":"X2","visibility":1,"length":3},
			{"id":3,"name":"X3","visibility":0,"length":3}
		]
		 */
		@RawString
		final String expectedBookmarks = lateInit();

		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("X")
				.setVisibility(PUBLIC)
		);
		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("X2")
				.setVisibility(PUBLIC)
		);
		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("X3")
				.setVisibility(PRIVATE)
		);

		// act
		final Response res = get("/api/v1.0/bookmark?from=1&quantity=2");

		// assert
		res
			.then()
			.assertThat()
			.statusCode(OK.getCode())
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(jsonMatchingPattern(expectedBookmarks, "**.id", "\\d+"))
		;


	}

	@Test
	void getV1_0ListBookmarksSearchSuccess(){

		// arrange

		/*
		[
			{"id":2,"name":"Android 7.0 was released","html":"Some desc","length":2, "visibility": 1},
			{"id":3,"name":"Separate your software release by major, minor and patch","length":2, "visibility": 0}
		]
		 */
		@RawString
		final String expectedBookmarks = lateInit();

		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("Google is the most popular search engine site")
				.setVisibility(PUBLIC)
		);
		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("Android 7.0 was released")
				.setDescription("Some desc")
				.setVisibility(PUBLIC)
		);
		bookmarksService.saveBookmark(
			new BookmarkEntity()
				.setName("Separate your software release by major, minor and patch")
				.setVisibility(PRIVATE)
		);

		// act
		final Response res = get("/api/v1.0/bookmark?from=0&quantity=3&query=release");

		// assert
		res
			.then()
			.assertThat()
			.statusCode(OK.getCode())
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(jsonMatchingPattern(expectedBookmarks, "**.id", "\\d+"))
		;

	}

}
