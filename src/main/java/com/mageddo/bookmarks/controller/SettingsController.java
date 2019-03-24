package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.entity.SettingEntity;
import com.mageddo.bookmarks.service.SettingsService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class SettingsController {

	private final SettingsService settingsService;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public SettingsController(SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	@Get("/api/v1.0/settings/map")
	public HttpResponse _1(){
		try {
			return ok(settingsService.findAllAsMap());
		} catch (Exception e){
			logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
			return badRequest("Could not read settings");
		}
	}

	@Get("/api/v1.0/settings")
	public HttpResponse _2(){
		try {
			return ok(settingsService.findAll());
		} catch (Exception e){
			logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
			return badRequest("Could not read settings");
		}
	}

	@Get("/api/v1.0/settings")
	public HttpResponse _3(@QueryValue("key") String key){
		try {
			if(StringUtils.isBlank(key)){
				return ok(settingsService.findAll());
			}
			return ok(settingsService.findSetting(key));
		} catch (Exception e){
			logger.error("status=cant-load-settings, msg={}", e.getMessage(), e);
			return badRequest("Could not read settings");
		}
	}

	@Patch("/api/v1.0/settings")
	public HttpResponse _4(@Body List<SettingEntity> settings){
		try {
			settingsService.patch(settings);
			return ok();
		} catch (Exception e){
			logger.error("status=cant-update-settings, msg={}", e.getMessage(), e);
			return badRequest("Could not update settings");
		}
	}
}
