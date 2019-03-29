package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.utils.ThymeleafUtils;
import com.mageddo.commons.Maps;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final BookmarksService bookmarksService;
	private final String analyticsId;

	public HomeController(BookmarksService bookmarksService, @Value("${analytics.id}") String analyticsId) {
		this.bookmarksService = bookmarksService;
		this.analyticsId = analyticsId;
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
				"msg", "No results",
				"thymeleafUtils", ThymeleafUtils.getInstance()
			));
		} else if (page > 0) {
			startPage = page * pageSize;
		}
		logger.debug("start=%d, size=%d, page=%d, pages=%d", startPage, size, page, pages);
		final List<RecentBookmarksRes> recentBookmarks = bookmarksService.getRecentBookmarks(pageSize, startPage);
		return ok(Maps.of(
			"analytics", analyticsId,
			"headerTitle", page == 0 ? "Home" : "Page " + (page + 1),
			"pageTitle", page == 0 ? null : "Page " + (page + 1),
			"description", "Technology posts based on my practice experience",
			"nextPage", page + 2,
			"hasMore", pages > page + 1,
			"bookmarks", recentBookmarks,
			"thymeleafUtils", ThymeleafUtils.getInstance()
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
