package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.service.BookmarkService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class SiteMapController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final BookmarkService bookmarkService;

	public SiteMapController(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}

	@Get("/api/v1.0/sitemap")
	public HttpResponse sitemap(HttpRequest req) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			this.bookmarkService.generateSiteMapXML(out, req.getPath());
			return ok(new String(out.toByteArray()));
		} catch (Exception e) {
			logger.error("status=cant-mount-sitemap, msg={}", e.getMessage(), e);
			return badRequest(e.getMessage());
		}

	}
}
