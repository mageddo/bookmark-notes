package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.commons.UrlUtils;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.RawStrings;
import com.mageddo.rawstringliterals.Rsl;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ModelAndView;
import com.mageddo.commons.Maps;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Rsl
@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final BookmarksService bookmarksService;

	public HomeController(BookmarksService bookmarksService) {
		this.bookmarksService = bookmarksService;
	}

	@Get("/robots.txt")
	HttpResponse robots(HttpRequest req){
/*
# Sitemaps
Sitemap: %s/api/v1.0/sitemap
*/
		@RawString
		final String robots = RawStrings.lateInit();
		return ok(String.format(robots, UrlUtils.getFullHost(req)))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN);
	}

	@Get
	@View("index")
	HttpResponse index(@QueryValue(value = "page", defaultValue = "1") int page) {
		page -= 1;
		final int size = bookmarksService.countPublicNotDeleted();
		int pageSize = 10, pages = (int) Math.ceil(size / (double) pageSize), startPage = 0;
		if (page + 1 > pages) {
			return ok(mapOf(
				"headerTitle", "Not found",
				"msg", "No results"
			));
		} else if (page > 0) {
			startPage = page * pageSize;
		}
		logger.debug("start={}, size={}}, page={}, pages={}", startPage, size, page, pages);
		final List<RecentBookmarksRes> recentBookmarks = bookmarksService.getRecentBookmarks(pageSize, startPage);
		return ok(Maps.of(
			"headerTitle", page == 0 ? "Home" : "Page " + (page + 1),
			"pageTitle", page == 0 ? null : "Page " + (page + 1),
			"description", "Technology posts based on my practice experience",
			"nextPage", page + 2,
			"hasMore", pages > page + 1,
			"bookmarks", recentBookmarks
		));
	}

	@Get("/_/bookmarks")
	@View("restricted-area/index")
	HttpResponse privateAreaIndex(@QueryValue(value = "page", defaultValue = "1") int page) {
		if(false){
			return ok(mapOf(
				"layout", false,
				"compile", false
			));
		} else {
			return ok(mapOf(
				"compile", false
			));
		}
	}

}
