package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.service.BookmarkService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static io.micronaut.http.HttpResponse.badRequest;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class BookmarkController {

	private final BookmarkService bookmarkService;

	public BookmarkController(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}

	@Get("/api/v1.0/sitemap")
	public HttpResponse sitemap(HttpRequest req) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			this.bookmarkService.generateSiteMapXML(out, req.getPath());
			return ok(new ByteArrayInputStream(out.toByteArray()));
		} catch (Exception e) {
			return badRequest(e.getMessage());
		}

	}
}
