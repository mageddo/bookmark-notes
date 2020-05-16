package com.mageddo.bookmarks.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.bookmarks.service.TagService;
import com.mageddo.commons.UrlUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;

import static com.mageddo.common.jackson.JsonUtils.writeValueAsString;
import static com.mageddo.markdown.MarkdownUtils.parseMarkdown;
import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;
import static io.micronaut.http.HttpResponse.serverError;

@Controller
public class BookmarkController {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final BookmarksService bookmarksService;
  private final TagService tagService;

  public BookmarkController(BookmarksService bookmarksService, TagService tagService) {
    this.bookmarksService = bookmarksService;
    this.tagService = tagService;
  }

  @Post(value = "/api/bookmark", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
  HttpResponse _3(@Body BookmarkRes bookmarkRes) {
    try {
      final BookmarkEntity bookmark = bookmarkRes.toBookmark();
      bookmarksService.createBookmark(bookmark, bookmarkRes.getTags());
      return ok(bookmark.getId());
    } catch (Exception e) {
      logger.error("status=cant-save-bookmark, msg={}", e.getMessage(), e);
      return serverError(mapOf("message", "Can't save bookmark")).header("caught", "1");
    }
  }

  @Put(value = "/api/bookmark", consumes = MediaType.APPLICATION_JSON)
  HttpResponse _4(@Body BookmarkRes bookmarkRes) {
    try {
      bookmarksService.updateBookmark(bookmarkRes.toBookmark(), bookmarkRes.getTags());
      return ok();
    } catch (Exception e) {
      logger.error("status=cant-update-bookmark, msg={}", e.getMessage(), e);
      return serverError(mapOf("message", "Can't Update bookmark")).header("caught", "1");
    }
  }

  @Delete(value = "/api/bookmark", produces = MediaType.TEXT_PLAIN)
  HttpResponse _5(@Body BookmarkRes bookmark) {
    try {
      bookmarksService.deleteBookmark(bookmark.getId());
      return ok();
    } catch (Exception e) {
      logger.error("status=cant-delete-bookmark, msg={}", e.getMessage(), e);
      return serverError(mapOf("message", "Can't Delete bookmark")).header("caught", "1");
    }
  }

  @Post(value = "/api/bookmark/recover", produces = MediaType.TEXT_PLAIN)
  HttpResponse _6(@Body BookmarkRes bookmark) {
    try {
      bookmarksService.recoverBookmark(bookmark.getId());
      return ok();
    } catch (Exception e) {
      logger.error("status=cant-recover-bookmark, msg={}", e.getMessage(), e);
      return serverError(mapOf("message", "Can't Recover bookmark")).header("caught", "1");
    }
  }

  @Get("/bookmark/edit")
  @View("restricted-area/bookmark-edit")
  HttpResponse _1(@QueryValue("id") long bookmarkId, @QueryValue("editMode") boolean editMode) {
    final BookmarkRes bookmark = bookmarksService.getBookmarkRes(bookmarkId);
    final List<TagEntity> tags = tagService.getTags(bookmarkId);
    return ok(mapOf("bookmark", bookmark, "tags", tags, "editMode", editMode, "tagsAsJson",
        writeValueAsString(tags.stream()
            .map(TagEntity::getName)
            .collect(Collectors.toList())
        )
    ));
  }

  @Get("/bookmark/new")
  @View("restricted-area/bookmark-edit")
  HttpResponse _2() {
    return ok(mapOf("tagsAsJson", "[]", "bookmark", new BookmarkRes()));
  }

  /**
   * Public bookmark view
   */
  @Get("/bookmark/{id}/{description}")
  @View("bookmark-view")
  HttpResponse _3(HttpRequest req, int id, String description) {

    logger.debug("status=begin, id={}, desc={}", id, description);
    final var bookmark = bookmarksService.findBookmarkWithNavigation(id);

    if (!bookmark.hasBookmark()) {
      logger.debug("status=not-found, id={}, desc={}", id, description);
      final var title = String.format("%s", description != null ? description : id);
      return ok(mapOf(
          "title", title,
          "description", title,
          "content", null
      ));
    }
    logger.debug("status=found, id={}, desc={}", id, description);
    return renderWhenFound(req, bookmark);
  }

  HttpResponse renderWhenFound(HttpRequest req, BookmarkDescriptionRes bookmark) {

    final var title = bookmark.getBookmark().getName();
    final var content = parseMarkdown(bookmark.getBookmark().getHtml());

    // if you make changes here probably you also want to change  public/js/ct/bookmarkEdit.js#267
//				content = parseCode(`
//					<div class="mg-code" lang="{{lang}}">
//						<ul class="nav nav-pills painel-acoes">
//							<li role="presentation" style="visibility: hidden;" class="pull-right" ><a href="#" class="skipped
//							glyphicon glyphicon-option-vertical"></a></li>
//						</ul>
//						<pre><code>{{{code}}}</code></pre>
//					</div>`, bookmark.bookmark.html);

    final var shortDesc = StringUtils
        .substring(
            this.clearHTML(content)
                .replace("/[\\r\\n]+ /", ""),
            0, 157
        )
        .concat("...");
    return ok(mapOf(
        "analytics", "",
        "creationDate", this.toSQLDate(bookmark.getBookmark().getCreationDate()),
        "updateDate", this.toSQLDate(bookmark.getBookmark().getUpdateDate()),
        "id", bookmark.getBookmark().getId(),
        "title", title,
        "description", shortDesc,
        "content", content,
        "link", this.createSEOLink(req, bookmark.getBookmark()),
        "prev", mapOf("name", this.getName(bookmark.getPrev()), "link", this.createSEOLink(req, bookmark.getPrev())),
        "next", mapOf("name", this.getName(bookmark.getNext()), "link", this.createSEOLink(req, bookmark.getNext()))
    ));
  }

  private String getName(BookmarkRes bookmark) {
    return Optional.ofNullable(bookmark)
        .map(BookmarkRes::getName)
        .orElse("");
  }

  private String createSEOLink(HttpRequest req, BookmarkRes bookmark) {
    if (bookmark == null) {
      return null;
    }
    return String.format("//%s/bookmark/%d/%s", UrlUtils.getHost(req), bookmark.getId(),
        UrlUtils.encodeSeoUrl(bookmark.getName())
    );
  }

  private String toSQLDate(LocalDateTime creationDate) {
    if (creationDate == null) {
      return "";
    }
    return creationDate.toLocalDate()
        .toString();
  }

  String clearHTML(String html) {
    return html.replaceAll("<[^>]*>", "");
  }

}
