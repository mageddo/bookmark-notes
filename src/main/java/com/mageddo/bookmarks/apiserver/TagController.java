package com.mageddo.bookmarks.apiserver;

import com.mageddo.bookmarks.service.TagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;
import static io.micronaut.http.HttpResponse.serverError;

@Controller
public class TagController {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final TagService tagService;

  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @Get("/api/tag")
  public HttpResponse _1() {
    try {
      return ok(tagService.getTags());
    } catch (Exception e) {
      logger.error("status=failed-load-tags, msg={}", e.getMessage(), e);
      return serverError(mapOf("message", "Opa, encontramos um problema ao carregar os bookmarks"));
    }
  }

  @Get("/api/tag/search")
  HttpResponse _2(@QueryValue("q") String query) {
    try {
      return ok(tagService.findTags(query));
    } catch (Exception e) {
      logger.error("status=cant-filter-tags, msg={}", e.getMessage(), e);
      return serverError(mapOf("mesage", "Erro ao buscar tags por: " + query)).header("caught", "1");
    }
  }
}
