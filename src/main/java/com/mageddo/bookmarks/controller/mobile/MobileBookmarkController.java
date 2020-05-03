package com.mageddo.bookmarks.controller.mobile;

import java.util.List;
import java.util.stream.Collectors;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.bookmarks.service.TagService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;

import static com.mageddo.bookmarks.entity.SettingEntity.Setting.MOBILE_CODE_BLOCK_MAX_HEIGHT;
import static com.mageddo.common.jackson.JsonUtils.writeValueAsString;
import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class MobileBookmarkController {

  private final SettingsService settingsService;
  private final BookmarksService bookmarksService;
  private final TagService tagService;

  public MobileBookmarkController(SettingsService settingsService, BookmarksService bookmarksService,
      TagService tagService) {
    this.settingsService = settingsService;
    this.bookmarksService = bookmarksService;
    this.tagService = tagService;
  }

  @Get("/mobile/bookmark/new")
  @View("mobile/restricted-area/bookmark-edit")
  public HttpResponse _1() {
    return ok(mapOf("mobileMaxHeight", settingsService.findSetting(MOBILE_CODE_BLOCK_MAX_HEIGHT.name())
        .getValue(), "bookmark", new BookmarkRes(), "tagsAsJson", "[]"));
  }

  @Get("/mobile/bookmark/edit")
  @View("mobile/restricted-area/bookmark-edit")
  public HttpResponse _2(@QueryValue("id") int bookmarkId) {
    final BookmarkRes bookmark = bookmarksService.getBookmarkRes(bookmarkId);
    final List<TagEntity> tags = tagService.getTags(bookmarkId);
    return ok(mapOf("mobileMaxHeight", settingsService.findSetting(MOBILE_CODE_BLOCK_MAX_HEIGHT.name())
        .getValue(), "bookmark", bookmark, "tags", tags, "tagsAsJson", writeValueAsString(tags.stream()
        .map(TagEntity::getName)
        .collect(Collectors.toList())), "editMode", true));
  }
}
