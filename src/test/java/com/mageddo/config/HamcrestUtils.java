package com.mageddo.config;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

public final class HamcrestUtils {
  private HamcrestUtils() {
  }

  public static Matcher<String> jsonMatchingPattern(String expected) {
    return jsonMatchingPattern(expected, true);
  }

  public static Matcher<String> jsonMatchingPattern(String expected, boolean strict) {
    return new BaseMatcher<String>() {
      @Override
      public void describeTo(Description description) {
        description.appendValue(expected);
      }

      @Override
      public boolean matches(Object actual) {
        try {
          JSONAssert.assertEquals(expected, (String) actual, strict);
          return true;
        } catch (JSONException e) {
          return false;
        }
      }
    };
  }

  public static Matcher<String> jsonMatchingPattern(String expected, String prop, String pattern) {
    return new BaseMatcher<String>() {
      @Override
      public void describeTo(Description description) {
        description.appendValue(expected);
      }

      @Override
      public boolean matches(Object actual) {
        try {
          JSONAssert.assertEquals(expected, (String) actual, JsonComparators.matchingPattern(prop, pattern));
          return true;
        } catch (JSONException e) {
          return false;
        }
      }
    };
  }

  public static Matcher<String> jsonMatchingPattern(String expected, String... props) {
    return new BaseMatcher<String>() {
      @Override
      public void describeTo(Description description) {
        description.appendValue(expected);
      }

      @Override
      public boolean matches(Object actual) {
        try {
          JSONAssert.assertEquals(expected, (String) actual, JsonComparators.matchingPattern(props));
          return true;
        } catch (JSONException e) {
          return false;
        }
      }
    };
  }

}
