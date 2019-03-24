package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.service.SiteMapService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class SiteMapController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final SiteMapService siteMapService;

	public SiteMapController(SiteMapService siteMapService) {
		this.siteMapService = siteMapService;
	}

	@Get("/api/v1.0/sitemap")
	@Produces(MediaType.APPLICATION_XML)
	public HttpResponse sitemap(HttpRequest req) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			this.siteMapService.generateSiteMapXML(out, req.getPath());
			return ok(new String(out.toByteArray()));
		} catch (Exception e) {
			logger.error("status=cant-mount-sitemap, msg={}", e.getMessage(), e);
			return badRequest(e.getMessage());
		}

	}
}
