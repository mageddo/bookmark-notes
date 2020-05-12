package com.mageddo.bookmarks.controller;

import java.util.List;

import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.common.jackson.JsonUtils;
import com.mageddo.commons.Maps;
import com.mageddo.commons.UrlUtils;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.RawStrings;
import com.mageddo.rawstringliterals.Rsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Rsl
@Controller
public class HomeController {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final BookmarksService bookmarksService;
  private final SettingsService settingsService;

  public HomeController(BookmarksService bookmarksService, SettingsService settingsService) {
    this.bookmarksService = bookmarksService;
    this.settingsService = settingsService;
  }

  @Get("/robots.txt")
  HttpResponse robots(HttpRequest req) {
/*Sitemap: %s/sitemap.xml
*/
    @RawString
    final String robots = RawStrings.lateInit();
    return ok(String.format(robots, UrlUtils.getFullHost(req))).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN);
  }

  @Get
  @View("index")
  HttpResponse index(@QueryValue(value = "page", defaultValue = "1") int page) {
    page -= 1;
    final int size = bookmarksService.countPublicNotDeleted();
    int pageSize = 10, pages = (int) Math.ceil(size / (double) pageSize), startPage = 0;
    if (page + 1 > pages) {
      return ok(mapOf("headerTitle", "Not found", "msg", "No results"));
    } else if (page > 0) {
      startPage = page * pageSize;
    }
    logger.debug("start={}, size={}}, page={}, pages={}", startPage, size, page, pages);
    final List<RecentBookmarksRes> recentBookmarks = bookmarksService.getRecentBookmarks(pageSize, startPage);
    return ok(Maps.of("headerTitle", page == 0 ? "Home" : "Page " + (page + 1), "pageTitle",
        page == 0 ? null : "Page " + (page + 1), "description", "Technology posts based on my practice experience",
        "nextPage", page + 2, "hasMore", pages > page + 1, "bookmarks", recentBookmarks
    ));
  }

  @Get("/_/bookmarks")
  @View("restricted-area/index")
  HttpResponse privateAreaIndex(@QueryValue(value = "page", defaultValue = "1") int page) {
    if (false) {
      return ok(mapOf("layout", false, "compile", false));
    } else {
      return ok(mapOf(
          "compile", false
          , "settingsJson", JsonUtils.writeValueAsString(settingsService.findAllAsMap())
      ));
    }
  }

}
