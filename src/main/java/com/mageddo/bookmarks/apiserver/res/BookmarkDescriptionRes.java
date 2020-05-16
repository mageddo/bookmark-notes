package com.mageddo.bookmarks.apiserver.res;

import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

public class BookmarkDescriptionRes {

  public static final int MIDDLE = 1;
  public static final int LEFT = 0;
  public static final int RIGHT = 2;
  public static final int EXPECTED_BOOKMARKS = 3;
  private BookmarkRes prev;
  private BookmarkRes bookmark;
  private BookmarkRes next;

  /**
   * @param bookmarkRes found bookmarks, expected to be sorted by id
   */
  public BookmarkDescriptionRes(List<BookmarkRes> bookmarkRes, int bookmarkId) {
    Validate.isTrue(
        bookmarkRes.size() <= EXPECTED_BOOKMARKS,
        "Max number of bookmarks must be %d", EXPECTED_BOOKMARKS
    );
    final var bookmarkIndex = this.indexOf(bookmarkRes, bookmarkId);
    if(bookmarkIndex == LEFT){
      this.bookmark = getIfPossible(bookmarkRes, LEFT);
      this.next = getIfPossible(bookmarkRes, MIDDLE);
    } else if(bookmarkIndex == MIDDLE){
      this.prev = getIfPossible(bookmarkRes, LEFT);
      this.bookmark = getIfPossible(bookmarkRes, MIDDLE);
      this.next = getIfPossible(bookmarkRes, RIGHT);
    } else if (bookmarkIndex == RIGHT){
      this.prev = getIfPossible(bookmarkRes, LEFT);
      this.bookmark = getIfPossible(bookmarkRes, RIGHT);
    }
  }

  private BookmarkRes getIfPossible(List<BookmarkRes> bookmarkRes, int i) {
    if(bookmarkRes.size() <= i){
      return null;
    }
    return bookmarkRes.get(i);
  }

  private int indexOf(List<BookmarkRes> bookmarks, int bookmarkId) {
    for (int i = LEFT; i < bookmarks.size(); i++) {
      final var bookmark = getIfPossible(bookmarks, i);
      if (Objects.equals(bookmark.getId(), bookmarkId)) {
        return i;
      }
    }
    throw new IllegalStateException("Bookmark is expected to be found " + bookmarkId);
  }

  public BookmarkRes getPrev() {
    return prev;
  }

  public BookmarkRes getBookmark() {
    return bookmark;
  }

  public BookmarkRes getNext() {
    return next;
  }

  public boolean hasBookmark() {
    return bookmark != null;
  }
}
