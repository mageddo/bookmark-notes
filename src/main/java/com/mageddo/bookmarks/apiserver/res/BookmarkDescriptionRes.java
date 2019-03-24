package com.mageddo.bookmarks.apiserver.res;

import java.util.List;
import java.util.Objects;

public class BookmarkDescriptionRes {

	private BookmarkRes prev;
	private BookmarkRes bookmark;
	private BookmarkRes next;

	public BookmarkDescriptionRes(List<BookmarkRes> bookmarkRes, int bookmarkId) {
		boolean foundBookmark = false;
		for (BookmarkRes bookmark : bookmarkRes) {
			if (Objects.equals(bookmark.getId(), bookmarkId)) {
				foundBookmark = true;
				this.bookmark = bookmark;
			} else {
				if (foundBookmark) {
					this.next = bookmark;
				} else {
					this.prev = bookmark;
				}
			}
		}
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

	public boolean hasBookmark(){
		return bookmark != null;
	}
}
