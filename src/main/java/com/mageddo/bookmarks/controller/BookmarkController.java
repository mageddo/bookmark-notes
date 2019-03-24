package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.entity.SettingEntity.Setting;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.bookmarks.service.TagService;
import com.mageddo.commons.URLUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mageddo.common.jackson.JsonUtils.writeValueAsString;
import static com.mageddo.commons.MarkdownUtils.parseMarkdown;
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

	@Post(value = "/api/bookmark", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
	HttpResponse _3(@Body BookmarkRes bookmarkRes) {
		try {
			final BookmarkEntity bookmark = bookmarkRes.toBookmark();
			bookmarksService.createBookmark(bookmark, bookmarkRes.getTags());
			return ok(bookmark.getId());
		} catch (Exception e) {
			logger.error("status=cant-save-bookmark, msg={}", e.getMessage(), e);
			return serverError(mapOf(
				"message", "Can't save bookmark"
			))
				.header("caught", "1");
		}
	}

	@Put(value = "/api/bookmark", consumes = MediaType.APPLICATION_JSON)
	HttpResponse _4(@Body BookmarkRes bookmarkRes) {
		try {
			bookmarksService.updateBookmark(bookmarkRes.toBookmark(), bookmarkRes.getTags());
			return ok();
		} catch (Exception e) {
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
	HttpResponse _2() {
		return ok(mapOf(
			"maxHeight", getMaxHeight(),
			"stringify", (Supplier) () -> "[]",
			"bookmark", new BookmarkRes()
		));
	}

	/**
	 * Public bookmark view
	 */
	@Get("/bookmark/{id}/{description}")
	@View("bookmark-view")
	HttpResponse _3(HttpRequest req, int id, String description) {

		logger.debug("status=begin, id={}, desc={}", id, description);
		final BookmarkDescriptionRes bookmark = bookmarksService.findBookmarkWithNavigation(id);
		String title;
		String content;
		if (bookmark.hasBookmark()) {
			title = bookmark.getBookmark().getName();

			content = parseMarkdown(bookmark.getBookmark().getHtml());

			// if you make changes here probably you also want to change  public/js/ct/bookmarkEdit.js#267
//				content = parseCode(`
//					<div class="mg-code" lang="{{lang}}">
//						<ul class="nav nav-pills painel-acoes">
//							<li role="presentation" style="visibility: hidden;" class="pull-right" ><a href="#" class="skipped glyphicon glyphicon-option-vertical"></a></li>
//						</ul>
//						<pre><code>{{{code}}}</code></pre>
//					</div>`, bookmark.bookmark.html);
		} else {
			title = String.format("Bookmark '%s' not found", description != null ? description : id);
			content = "";
		}

		return ok(mapOf(
			"analytics", "",
			"creationDate", toSQLDate(bookmark.getBookmark().getCreationDate()),
			"updateDate", toSQLDate(bookmark.getBookmark().getUpdateDate()),
			"id", id,
			"title", title,
			"content", content,
			"description", StringUtils.substring(clearHTML(content).replace( "/[\\r\\n]+ /", ""), 0, 160),
			"prev", mapOf(
				"name", getName(bookmark.getPrev()),
				"link", createSEOLink(req, bookmark.getPrev())
			),
			"next", mapOf(
				"name", getName(bookmark.getNext()),
				"link", createSEOLink(req, bookmark.getNext())
			)
		));
	}

	private String getName(BookmarkRes bookmark) {
		return Optional.ofNullable(bookmark).map(BookmarkRes::getName).orElse("");
	}

	private String createSEOLink(HttpRequest req, BookmarkRes bookmark) {
		if(bookmark == null){
			return null;
		}
		final String host = req.getHeaders().get("Host");
		return String.format(
			"//%s/bookmark/%d/%s",
			host, bookmark.getId(), URLUtils.encode(normalizeURL(bookmark.getName()))
		);
	}

	String normalizeURL(String path){
		return StringUtils.stripAccents(path.toLowerCase().replaceAll("\\s", "-"));
	}

	private String toSQLDate(LocalDateTime creationDate) {
		if (creationDate == null) {
			return "";
		}
		return creationDate.toLocalDate().toString();
	}

	private String getMaxHeight() {
		return settingsService.findSetting(Setting.CODE_BLOCK_MAX_HEIGHT.name()).getValue();
	}

	String clearHTML(String html) {
		return html.replace("/<\\/?[^>]+(>|$)/g", "");
	}

}
