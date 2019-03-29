package com.mageddo.config;

import org.apache.commons.lang3.Validate;
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

	public static JSONComparator matchingPattern(String... props){
		return matchingPattern(JSONCompareMode.STRICT, props);
	}

	public static JSONComparator matchingPattern(JSONCompareMode compareMode, String... props){
		Validate.isTrue(props.length % 2 == 0, "Should have even quantity " + props.length);
		final Customization[] customizations = new Customization[props.length / 2];
		for (int i = 0; i < props.length; i++) {
			if(i % 2 == 0){
				customizations[i / 2] = new Customization(props[i], new RegularExpressionValueMatcher<>(props[i + 1]));
			}
		}
		return new CustomComparator(compareMode, customizations);
	}
}
