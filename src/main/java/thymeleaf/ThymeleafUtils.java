package thymeleaf;

import com.mageddo.commons.URLUtils;

import static com.mageddo.config.ApplicationContextUtils.context;

public final class ThymeleafUtils {

	private ThymeleafUtils() {
	}

	public static ThymeleafUtils getInstance() {
		return new ThymeleafUtils();
	}

	public static String[] splitTags(String tags) {
		return tags == null ? null : tags.split(",");
	}

	public static String encodePath(String path){
		return URLUtils.encode(path);
	}

	public static String encodeSeoUrl(String path){
		return URLUtils.encodeSeoUrl(path);
	}

	public static String analyticsId(){
		return context()
			.getEnvironment()
			.get("analytics.id", String.class, "")
			;
	}
}
