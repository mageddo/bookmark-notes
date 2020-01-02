package thymeleaf;

import com.mageddo.commons.UrlUtils;

import static com.mageddo.config.ApplicationContextUtils.context;

public final class ThymeleafUtils {

	private ThymeleafUtils() {
	}

	public static String[] splitTags(String tags) {
		return tags == null ? null : tags.split(",");
	}

	public static String encodePath(String path){
		return UrlUtils.encode(path);
	}

	public static String encodeSeoUrl(String path){
		return UrlUtils.encodeSeoUrl(path);
	}

	public static String analyticsId(){
		return context()
			.getEnvironment()
			.get("analytics.id", String.class, "")
			;
	}
}
