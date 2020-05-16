package thymeleaf;

import com.mageddo.bookmarks.service.SiteMapService;
import com.mageddo.commons.UrlUtils;

import static com.mageddo.config.ApplicationContextUtils.context;

public final class ThymeleafUtils {

  private ThymeleafUtils() {
  }

  public static String[] splitTags(String tags) {
    return tags == null ? null : tags.split(",");
  }

  public static String encodePath(String path) {
    return UrlUtils.encode(path);
  }

  public static String createBookmarkUrl(long id, String name) {
    return SiteMapService.formatUrl(id, name);
  }

  public static String analyticsId() {
    return context().getEnvironment()
        .get("analytics.id", String.class, "");
  }
}
