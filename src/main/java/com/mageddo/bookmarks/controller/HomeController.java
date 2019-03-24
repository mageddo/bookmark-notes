package com.mageddo.bookmarks.controller;

import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.service.BookmarksService;
import com.mageddo.commons.Maps;
import com.mageddo.commons.URLUtils;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static io.micronaut.core.util.CollectionUtils.mapOf;
import static io.micronaut.http.HttpResponse.ok;

@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final BookmarksService bookmarksService;
	private final String analyticsId;

	public HomeController(BookmarksService bookmarksService, @Value("${analytics.id}") String analyticsId) {
		this.bookmarksService = bookmarksService;
		this.analyticsId = analyticsId;
	}

	@Get
	@View("index")
	HttpResponse index(@QueryValue(value = "page", defaultValue = "1") int page){
		page -= 1;
		final int size = bookmarksService.countPublicNotDeleted();
		int pageSize = 10, pages = (int) Math.ceil(size / (double) pageSize), startPage = 0;
		if (page + 1 > pages){
			return ok(mapOf(
				"headerTitle", "Not found",
				"msg", "No results",
				"getURL", (Function<String, String>) URLUtils::getURL
			));
		} else if(page > 0){
			startPage = page * pageSize;
		}
		logger.debug("start=%d, size=%d, page=%d, pages=%d", startPage, size, page, pages);
		final List<RecentBookmarksRes> recentBookmarks = bookmarksService.getRecentBookmarks(pageSize, startPage);
		return ok(Maps.of(
			"analytics", analyticsId,
			"headerTitle", page == 0 ? "Home" : "Page " + (page + 1),
			"pageTitle", page == 0 ? null : "Page " + (page + 1),
			"description", "Technology posts based on my practice experience",
			"nextPage", page + 2,
			"hasMore", pages > page + 1,
			"layout", "publicLayout",
			"bookmarks", recentBookmarks,
			"toTagArray", (Function<String, String[]>) tags -> tags.equals("{NULL}") ? null : tags.split(","),
			"getURL", (Function<String, String>) URLUtils::getURL
		));
//		getURL(){
//			return function(path, render){
//				return utils.getSEOURL(req, render(path))
//			};
//		}
	}
//	app.get("/", function(req, res){
//
//	var page = parseInt(url.parse(req.url, true).query.page || 1) - 1;
//	m.countPublicNotDeletedBookmarks(function(err, size){
//		if(err){
//			return res.render("503", {
//				layout: false
//			});
//		}
//
//		var pageSize = 10, pages = Math.ceil(size / pageSize), startPage = 0;
//		if (page + 1 > pages){
//			return res.render("index", {
//				title: "Not found",
//				layout: "publicLayout",
//				msg: "No results",
//				getURL(){
//				return function(path, render){
//					return utils.getURL(req, render(path))
//				};
//			}
//			});
//		} else if(page > 0){
//			startPage = page * pageSize;
//		}
//
//		console.debug("m=getRecentBookmarks, start=%d, size=%d, page=%d, pages=%d", startPage, size, page, pages)
//		m.getRecentBookmarks(pageSize, startPage, function(err, data){
//			console.debug("m=getRecentBookmarks, err=%j", err)
//			if(err){
//				return res.render("503", {
//					layout: false
//				});
//			}
//			return res.render("index", {
//				analytics: config.get("analytics.id"),
//				title: page == 0 ? "Home" : "Page " + (page + 1),
//				pageTitle: page == 0 ? null : "Page " + (page + 1),
//				description: "Technology posts based on my practice experience",
//				nextPage: page + 2,
//				hasMore: pages > page + 1,
//				layout: "publicLayout",
//				bookmarks: data,
//				toTagArray(){
//				return function(render){
//					if(this.tags){
//						this.tags = this.tags.split(",");
//					}
//				}
//			},
//			getURL(){
//				return function(path, render){
//					return utils.getSEOURL(req, render(path))
//				};
//			}
//			});
//		})
//
//	})
//
//});

//	app.get("/", function(req, res){
//
//		var page = parseInt(url.parse(req.url, true).query.page || 1) - 1;
//		m.countPublicNotDeletedBookmarks(function(err, size){
//			if(err){
//				return res.render("503", {
//					layout: false
//				});
//			}
//
//			var pageSize = 10, pages = Math.ceil(size / pageSize), startPage = 0;
//			if (page + 1 > pages){
//				return res.render("index", {
//					title: "Not found",
//					layout: "publicLayout",
//					msg: "No results",
//					getURL(){
//					return function(path, render){
//						return utils.getURL(req, render(path))
//					};
//				}
//				});
//			} else if(page > 0){
//				startPage = page * pageSize;
//			}
//
//			console.debug("m=getRecentBookmarks, start=%d, size=%d, page=%d, pages=%d", startPage, size, page, pages)
//			m.getRecentBookmarks(pageSize, startPage, function(err, data){
//				console.debug("m=getRecentBookmarks, err=%j", err)
//				if(err){
//					return res.render("503", {
//						layout: false
//					});
//				}
//				return res.render("index", {
//					analytics: config.get("analytics.id"),
//					title: page == 0 ? "Home" : "Page " + (page + 1),
//					pageTitle: page == 0 ? null : "Page " + (page + 1),
//					description: "Technology posts based on my practice experience",
//					nextPage: page + 2,
//					hasMore: pages > page + 1,
//					layout: "publicLayout",
//					bookmarks: data,
//					toTagArray(){
//					return function(render){
//						if(this.tags){
//							this.tags = this.tags.split(",");
//						}
//					}
//				},
//				getURL(){
//					return function(path, render){
//						return utils.getSEOURL(req, render(path))
//					};
//				}
//				});
//			})
//
//		})
//
//	});
}
