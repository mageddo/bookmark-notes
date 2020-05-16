package com.mageddo.bookmarks.service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Singleton;

import com.mageddo.bookmarks.dao.BookmarkDAO;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.commons.UrlUtils;
import com.mageddo.rawstringliterals.RawString;
import com.mageddo.rawstringliterals.Rsl;

import io.micronaut.http.HttpRequest;
import io.micronaut.spring.tx.annotation.Transactional;

import static com.mageddo.rawstringliterals.RawStrings.lateInit;

@Rsl
@Singleton
public class SiteMapService {

  private final BookmarkDAO bookmarkDAO;

  public SiteMapService(BookmarkDAO bookmarkDAO) {
    this.bookmarkDAO = bookmarkDAO;
  }

  @Transactional
  public void generateSiteMapXML(OutputStream out, HttpRequest req) throws IOException {
    /*
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

    for (final var bookmarkEntity : bookmarkDAO.loadSiteMap()) {
      out.write(String.format(
          siteMapItem, this.formatURL(req, bookmarkEntity),
          this.formatDate(bookmarkEntity.getLastUpdate())
      ).getBytes());
    }
    out.write("</urlset>\n".getBytes());

  }

  private String formatDate(LocalDateTime date) {
    if (date == null) {
      return "";
    }
    return String.format("<lastmod>%s</lastmod>", date.format(DateTimeFormatter.ISO_DATE));
  }

  private String formatURL(HttpRequest url, BookmarkEntity bookmarkEntity) {
    return String.format(
        "%s%s",
        UrlUtils.getFullHost(url),
        formatUrl(bookmarkEntity.getId(), bookmarkEntity.getName())
    );
  }

  public static String formatUrl(long bookmarkId, String bookmarkName){
    return String.format("/bookmark/%d/%s", bookmarkId, UrlUtils.encodeSeoUrl(bookmarkName));
  }
}
