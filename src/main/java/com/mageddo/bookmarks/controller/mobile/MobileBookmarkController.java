package com.mageddo.bookmarks.controller.mobile;

import java.util.List;
import java.util.stream.Collectors;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.TagService;
import com.mageddo.common.jackson.JsonUtils;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class MobileBookmarkController {

  private final BookmarksService bookmarksService;
  private final TagService tagService;

  public MobileBookmarkController(BookmarksService bookmarksService, TagService tagService) {
    this.bookmarksService = bookmarksService;
    this.tagService = tagService;
  }

  @Get("/mobile/bookmark/new")
  @View("mobile/restricted-area/bookmark-edit")
  public HttpResponse _1() {
    return ok(mapOf("bookmark", new BookmarkRes(), "tagsAsJson", "[]"));
  }

  @Get("/mobile/bookmark/edit")
  @View("mobile/restricted-area/bookmark-edit")
  public HttpResponse _2(@QueryValue("id") int bookmarkId) {
    final List<TagEntity> tags = tagService.getTags(bookmarkId);
    final String tagsAsJson = JsonUtils.writeValueAsString(tags.stream()
        .map(TagEntity::getName)
        .collect(Collectors.toList()));
    final BookmarkRes bookmark = bookmarksService.getBookmarkRes(bookmarkId);
    return ok(mapOf("bookmark", bookmark, "tags", tags, "tagsAsJson", tagsAsJson, "editMode", true));
  }
}
