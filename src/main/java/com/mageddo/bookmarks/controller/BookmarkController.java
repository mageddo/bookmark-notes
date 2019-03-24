package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.entity.SettingEntity.Setting;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.bookmarks.service.TagService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mageddo.common.jackson.JsonUtils.writeValueAsString;
import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;
import static io.micronaut.http.HttpResponse.serverError;

@Controller
public class BookmarkController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final BookmarksService bookmarksService;
	private final TagService tagService;
	private final SettingsService settingsService;

	public BookmarkController(BookmarksService bookmarksService, TagService tagService, SettingsService settingsService) {
		this.bookmarksService = bookmarksService;
		this.tagService = tagService;
		this.settingsService = settingsService;
	}

	@Post(value = "/api/bookmark", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.TEXT_PLAIN)
	HttpResponse _3(@Body BookmarkRes bookmarkRes){
		try {
			final BookmarkEntity bookmark = bookmarkRes.toBookmark();
			bookmarksService.createBookmark(bookmark, bookmarkRes.getTags());
			return ok(bookmark.getId());
		} catch (Exception e){
			logger.error("status=cant-save-bookmark, msg={}", e.getMessage(), e);
			return serverError(mapOf(
				"message", "Can't save bookmark"
			))
			.header("caught", "1");
		}
	}

	@Put(value = "/api/bookmark", consumes = MediaType.APPLICATION_FORM_URLENCODED)
	HttpResponse _4(@Body BookmarkRes bookmarkRes){
		try {
			bookmarksService.updateBookmark(bookmarkRes.toBookmark(), bookmarkRes.getTags());
			return ok();
		} catch (Exception e){
			logger.error("status=cant-update-bookmark, msg={}", e.getMessage(), e);
			return serverError(mapOf(
				"message", "Can't Update bookmark"
			))
			.header("caught", "1");
		}
	}

	@Get("/bookmark/edit")
	@View("restricted-area/bookmark-edit")
	HttpResponse _1(@QueryValue("id") long bookmarkId, @QueryValue("editMode") boolean editMode) {
		final BookmarkRes bookmark = bookmarksService.getBookmarkRes(bookmarkId);
		final List<TagEntity> tags = tagService.getTags(bookmarkId);
		return ok(mapOf(
			"maxHeight", getMaxHeight(),
			"bookmark", bookmark,
			"tags", tags,
			"editMode", editMode,
			"stringify", (Supplier) () -> writeValueAsString(tags.stream().map(TagEntity::getName).collect(Collectors.toList()))
		));
	}

	@Get("/bookmark/new")
	@View("restricted-area/bookmark-edit")
	HttpResponse _2(){
		return ok(mapOf(
			"maxHeight", getMaxHeight(),
			"stringify", (Supplier) () -> "[]",
			"bookmark", new BookmarkRes()
		));
	}

	private String getMaxHeight() {
		return settingsService.findSetting(Setting.CODE_BLOCK_MAX_HEIGHT.name()).getValue();
	}
}

