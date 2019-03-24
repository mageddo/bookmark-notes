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
import static com.mageddo.rawstringliterals.commons.StringUtils.align;
import static io.micronaut.http.HttpStatus.OK;
import static io.restassured.RestAssured.get;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.hamcrest.CoreMatchers.equalTo;

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
//		databaseConfigurator.resetSequences();
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
			.body(equalTo(remove(align(expectedValidationMsg), '\n')))
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
	void GetV1_0ListBookmarksValidateFromSuccess(){

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

}


//
//	func TestGetV1_0ListBookmarksSearchSuccess(t *testing.T){
//
//		test.BuildDatabase()
//
//		expectedBookmarks := `[{"id":2,"name":"Android 7.0 was released","html":"","length":2},{"id":3,"name":"Separate your software release by major, minor and patch","html":"","length":2}]`
//
//		service.NewBookmarkService().SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Google is the most popular search engine site", entity.PUBLIC))
//		service.NewBookmarkService().SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Android 7.0 was released", entity.PUBLIC))
//		service.NewBookmarkService().SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Separate your software release by major, minor and patch", entity.PRIVATE))
//
//		resp, c, err := test.NewReq("GET", BOOKMARK_V1 + "?from=0&quantity=3&query=release")
//
//		assert.Nil(t, err)
//		assert.Equal(t, 200, c)
//
//		resp = regex.ReplaceAllString(resp, "")
//
//		assert.Equal(t, len(expectedBookmarks), len(resp))
//		assert.Equal(t, expectedBookmarks, resp)
//
//	}
//
//
