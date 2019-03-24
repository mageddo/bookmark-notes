package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.SettingEntity;
import com.mageddo.bookmarks.entity.SettingEntity.Setting;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.bookmarks.service.TagService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mageddo.common.jackson.JsonUtils.writeValueAsString;
import static io.micronaut.core.util.CollectionUtils.mapOf;

@Controller
public class BookmarkController {

	private final BookmarksService bookmarksService;
	private final TagService tagService;
	private final SettingsService settingsService;

	public BookmarkController(BookmarksService bookmarksService, TagService tagService, SettingsService settingsService) {
		this.bookmarksService = bookmarksService;
		this.tagService = tagService;
		this.settingsService = settingsService;
	}

	@Get("/bookmark/edit")
	ModelAndView _1(@QueryValue("id") long bookmarkId, @QueryValue("editMode") boolean editMode) {
		final BookmarkRes bookmark = bookmarksService.getBookmarkRes(bookmarkId);
		final List<TagEntity> tags = tagService.getTags(bookmarkId);
		final SettingEntity setting = settingsService.findSetting(Setting.CODE_BLOCK_MAX_HEIGHT.name());
		return new ModelAndView("restricted-area/bookmark-edit", mapOf(
			"maxHeight", setting.getValue(),
			"bookmark", bookmark,
			"tags", tags,
			"editMode", editMode,
			"stringify", (Supplier) () -> writeValueAsString(tags.stream().map(TagEntity::getName).collect(Collectors.toList())),
			"getVisibilityFlag", (Function<Integer, String>) this::getVisibilityFlag
		));
	}

	String getVisibilityFlag(int number) {
		if (number == 1) {
			return " checked";
		}
		return "";
	}
}

