package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.enums.BookmarkVisibility;
import com.mageddo.bookmarks.service.BookmarkService;
import com.mageddo.config.DatabaseConfigurator;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static com.mageddo.config.TestUtils.setupRestAssured;
import static com.mageddo.rawstringliterals.commons.StringUtils.align;
import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Rsl
@MicronautTest(environments = "pg")
public class SiteMapControllerIntTest {

	@Inject
	private EmbeddedServer server;

	@Inject
	private DatabaseConfigurator databaseConfigurator;

	@Inject
	private BookmarkService bookmarkService;

	@BeforeEach
	public void before(){
		setupRestAssured(server);
		databaseConfigurator.migrate();
	}

	@Test
	public void TestGetV1_0Success(){

		// arrange

		bookmarkService.saveBookmark(
			new BookmarkEntity()
				.setName("Awesome Bookmark")
				.setVisibility(BookmarkVisibility.PUBLIC)
				.setLastUpdate(LocalDateTime.parse("2017-08-07T00:00:00"))
		);

		bookmarkService.saveBookmark(
			new BookmarkEntity()
				.setName("Private Bookmark")
				.setVisibility(BookmarkVisibility.PRIVATE)
				.setLastUpdate(LocalDateTime.parse("2017-08-06T00:00:00"))
		);

		// act
		final Response req = get("/api/v1.0/sitemap");
		final String body = req
			.andReturn()
			.body()
			.asString();

		//assert
		req
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.getCode());

		final XmlPath xml = XmlPath.from(align(body));

		assertEquals(1, xml.getList("urlset").size());
		assertThat(xml.get("urlset[0].url.loc"), containsString("/api/v1.0/sitemap/bookmark/"));
		assertThat(xml.get("urlset[0].url.loc"), endsWith("/awesome-bookmark"));
		assertThat(xml.get("urlset[0].url.changefreq"), equalTo("weekly"));
		assertThat(xml.get("urlset[0].url.priority"), equalTo("1"));
		assertThat(body, xml.get("urlset[0].url.lastmod"), equalTo("2017-08-07"));


	}

}
