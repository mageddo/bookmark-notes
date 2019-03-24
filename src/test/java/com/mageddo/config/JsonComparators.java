package com.mageddo.config;

import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

public final class JsonComparators {
	private JsonComparators() {
	}

	public static JSONComparator matchingPattern(String prop, String regex){
		return new CustomComparator(
			JSONCompareMode.STRICT,
			new Customization(prop, new RegularExpressionValueMatcher<>(regex))
		);
	}
}
