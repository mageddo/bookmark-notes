package com.mageddo.bookmarks.apiserver;

import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.commons.Maps;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class BookmarkController {

	public static final int PAGE_SIZE = 100;
	private final BookmarksService bookmarksService;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BookmarkController(BookmarksService bookmarksService) {
		this.bookmarksService = bookmarksService;
	}

	@Get("/api/v1.0/bookmark")
	HttpResponse _1(
		@QueryValue(value = "from", defaultValue = "0") int from, @QueryValue(value = "quantity", defaultValue = "0") int quantity,
		@QueryValue(value = "tag", defaultValue = "") String tag, @QueryValue(value = "query", defaultValue = "") String query
	) {

		if (quantity <= 0) {
			return badRequest(Maps.of(
				"code", HttpStatus.BAD_REQUEST.getCode(),
				"message", "Please pass a valid quantity"
			))
			.header("catch", "1");
		}

		try {
			return ok(bookmarksService.getBookmarks(query, tag, from, quantity));
		} catch (Exception e) {
			logger.error("status=failed-load-bookmark, msg={}", e.getMessage(), e);
			return badRequest("Could not read bookmarks");
		}
	}

	@Get("/api/bookmark/search")
	public HttpResponse _4(@QueryValue String query, @QueryValue(defaultValue = "") String tag) {
		return _1(0, PAGE_SIZE, tag, query);
	}

	@Get("/api/bookmark")
	HttpResponse _2() {
		return _1(0, PAGE_SIZE, "", "");
	}

}
