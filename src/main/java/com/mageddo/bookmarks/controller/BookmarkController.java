package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.service.BookmarkService;
import org.springframework.web.reactive.function.server.RouterFunctions;

import java.io.ByteArrayOutputStream;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class BookmarkController {

	private final BookmarkService bookmarkService;

	public BookmarkController(BookmarkService bookmarkService) {

		this.bookmarkService = bookmarkService;

//		get("api/v1.0/sitemap", (req, res) -> {
//			try {
//				this.bookmarkService.generateSiteMapXML(res.raw().getOutputStream(), req.url());
//				return null;
//			} catch (Exception e){
//				halt(HttpStatus.BAD_REQUEST_400, "I don't think so!!!");
//				return e.getMessage();
//			}
//		});
	}

	public void handle(RouterFunctions.Builder router) {
		router.GET("/api/v1.0/sitemap", req -> {
			try {
				final ByteArrayOutputStream bout = new ByteArrayOutputStream();
				this.bookmarkService.generateSiteMapXML(bout, req.path());
				return ok().syncBody(new String(bout.toByteArray()));
			} catch (Exception e){
				return badRequest().syncBody(e.getMessage());
			}
		});
	}
}
