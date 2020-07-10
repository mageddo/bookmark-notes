package thymeleaf;

import com.mageddo.bookmarks.entity.SettingEntity.Setting;
import com.mageddo.bookmarks.service.SettingsService;
import com.mageddo.bookmarks.service.SiteMapService;
import com.mageddo.commons.UrlUtils;

import org.commonmark.internal.util.Html5Entities;
import org.unbescape.html.HtmlEscape;

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

  public static String headerHtml(){
    return HtmlEscape.unescapeHtml(context()
        .getBean(SettingsService.class)
        .findSetting(Setting.PUBLIC_PAGES_HEADER_HTML.name())
        .getValue())
        ;
  }
}
