package com.mageddo.bookmarks.enums;

public enum BookmarkVisibility {

  PRIVATE(0), PUBLIC(1);

  private final int code;

  BookmarkVisibility(int code) {
    this.code = code;
  }

  public static BookmarkVisibility mustFromCode(int code) {
    for (BookmarkVisibility visibility : values()) {
      if (visibility.getCode() == code) {
        return visibility;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }
}
