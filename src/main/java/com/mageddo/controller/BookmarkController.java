package com.mageddo.controller;

import com.mageddo.bookmarks.service.BookmarkService;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.get;
import static spark.Spark.halt;

public class BookmarkController {

	private final BookmarkService bookmarkService;

	public BookmarkController(BookmarkService bookmarkService) {

		this.bookmarkService = bookmarkService;

		get("api/v1.0/sitemap", (req, res) -> {
			try {
				this.bookmarkService.generateSiteMapXML(res.raw().getOutputStream(), req.url());
				return null;
			} catch (Exception e){
				halt(HttpStatus.BAD_REQUEST_400, "I don't think so!!!");
				return e.getMessage();
			}
		});
	}
}
