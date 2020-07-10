package com.mageddo.bookmarks.apiserver;

import com.mageddo.bookmarks.entity.SettingEntity;
import com.mageddo.bookmarks.exception.NotFoundException;
import com.mageddo.bookmarks.service.SettingsService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.QueryValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unbescape.html.HtmlEscape;

import java.util.List;
import java.util.stream.Collectors;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.notFound;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class SettingsController {

  private final SettingsService settingsService;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  public SettingsController(SettingsService settingsService) {
    this.settingsService = settingsService;
  }

  @Get("/api/v{version:[12]}.0/settings/map")
  public HttpResponse _1(String version) {
    try {
      return ok(settingsService.findAllAsMap());
    } catch (Exception e) {
      logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
      return badRequest("Could not read settings");
    }
  }

  @Get("/api/v{version:[12]}.0/settings")
  public HttpResponse _2(String version) {
    try {
      return ok(settingsService.findAll());
    } catch (Exception e) {
      logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
      return badRequest("Could not read settings");
    }
  }

  @Get("/api/v{version:[12]}.0/settings?key")
  public HttpResponse _3(String version, @QueryValue("key") String key) {
    try {
      return ok(settingsService.findSetting(key));
    } catch (Exception e) {
      logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
      return badRequest("Could not read settings");
    }
  }

  @Patch(value = "/api/v{version:[12]}.0/settings", consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public HttpResponse _4(String version, @Body List<SettingEntity> settings) {
    try {
      settingsService.patch(
          settings
              .stream()
              .map(it -> it.setValue(HtmlEscape.escapeHtml4(it.getValue())))
              .collect(Collectors.toList())
      );
      return ok();
    } catch (NotFoundException e) {
      logger.warn("status=not-found, msg={}", e.getMessage(), e);
      return notFound();
    } catch (Exception e) {
      logger.error("status=cant-update-settings, msg={}", e.getMessage(), e);
      return badRequest("Could not update settings");
    }
  }
}
