package com.mageddo.bookmarks.service;

import com.mageddo.bookmarks.dao.BookmarkDAO;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Service
public class BookmarkService {

	private final BookmarkDAO bookmarkDAO;

	public BookmarkService(BookmarkDAO bookmarkDAO) {
		this.bookmarkDAO = bookmarkDAO;
	}

	@Transactional
	public void generateSiteMapXML(OutputStream out, String url) throws IOException {

		/*
		<?xml version="1.0" encoding="UTF-8"?>
		<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
		 */
		@RawString
		final String header = lateInit();
		out.write(header.getBytes());

		/*
		<url>
			<loc>%s</loc>
			%s
			<changefreq>weekly</changefreq>
			<priority>1</priority>
		</url>
		 */
		@RawString
		final String siteMapItem = lateInit();

		for (final BookmarkEntity bookmarkEntity : bookmarkDAO.loadSiteMap()) {
			out.write(String.format(
				siteMapItem, formatURL(url, bookmarkEntity), formatDate(bookmarkEntity.getLastUpdate())
			).getBytes());
		}
		out.write( "</urlset>\n".getBytes());

	}

	private String formatDate(LocalDateTime date) {
		if(date == null){
			return "";
		}
		return date.format(DateTimeFormatter.ISO_DATE);
	}

	private String formatURL(String url, BookmarkEntity bookmarkEntity) {
		return String.format(
			"%s/bookmark/%d/%s", url, bookmarkEntity.getId(),
			bookmarkEntity.getName().toLowerCase().replaceAll(" ", "-")
		);
	}
}
