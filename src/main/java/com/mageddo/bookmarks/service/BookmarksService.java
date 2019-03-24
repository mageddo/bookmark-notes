package com.mageddo.bookmarks.service;

import com.mageddo.bookmarks.apiserver.res.BookmarkDescriptionRes;
import com.mageddo.bookmarks.apiserver.res.BookmarkRes;
import com.mageddo.bookmarks.apiserver.res.RecentBookmarksRes;
import com.mageddo.bookmarks.dao.BookmarkDAO;
import com.mageddo.bookmarks.dao.TagDAO;
import com.mageddo.bookmarks.entity.BookmarkEntity;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.utils.Tags;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Singleton
public class BookmarksService {

	private static final int SEARCH_MIN_SIZE = 3;
	private final BookmarkDAO bookmarkDAO;
	private final TagDAO tagDAO;

	public BookmarksService(BookmarkDAO bookmarkDAO, TagDAO tagDAO) {
		this.bookmarkDAO = bookmarkDAO;
		this.tagDAO = tagDAO;
	}

	@Transactional
	public void createBookmark(BookmarkEntity bookmarkEntity, List<String> rawTags){
		bookmarkDAO.createBookmark(bookmarkEntity);
		associateTags(rawTags, bookmarkEntity.getId());
	}

	public List<BookmarkRes> getBookmarks(String query, String tag, int offset, int quantity){

		tag = tag.trim();
		query = query.trim();

		if (isNotBlank(tag)) {
			return bookmarkDAO.getBookmarks(tag, offset, quantity);
		} else if (isNotBlank(query)) {
			if (query.length() < SEARCH_MIN_SIZE) {
				throw new IllegalArgumentException(String.format("Search text must have %d characters at least", SEARCH_MIN_SIZE));
			}
			return bookmarkDAO.getBookmarksByNameOrContent(query, offset, quantity);
		}
		return bookmarkDAO.getBookmarks(offset, quantity);
	}

	public int countPublicNotDeleted() {
		return bookmarkDAO.countPublicNotDeleted();
	}

	public List<RecentBookmarksRes> getRecentBookmarks(int pageSize, int startPage) {
		return bookmarkDAO.getRecentBookmarks(pageSize, startPage);
	}

	public BookmarkRes getBookmarkRes(long bookmarkId) {
		return bookmarkDAO.findBookmarkRes(bookmarkId);
	}

	public void updateBookmark(BookmarkEntity bookmark, List<String> rawTags) {
		bookmarkDAO.update(bookmark);
		bookmarkDAO.disassociateTags(bookmark.getId());
		associateTags(rawTags, bookmark.getId());
	}

	private void associateTags(List<String> rawTags, long bookmarkId) {
		for (final TagEntity tagEntity : createIgnoreDuplicates(rawTags)) {
			bookmarkDAO.associate(tagEntity.getId(), bookmarkId);
		}
	}

	private List<TagEntity> createIgnoreDuplicates(List<String> rawTags) {
		final Set<Tags.Tag> tags = Tags.toTags(rawTags);
		final List<TagEntity> updatedTags = new ArrayList<>();
		for (Tags.Tag tag : tags) {
			tagDAO.createIgnoreDuplicates(tag);
			updatedTags.add(tagDAO.findTag(tag.getSlug()));
		}
		return updatedTags;
	}

	public BookmarkRes findBookmark(int id) {
		return bookmarkDAO.findBookmarkRes(id);
	}

	public BookmarkDescriptionRes findBookmarkWithNavigation(int bookmarkId) {
		return bookmarkDAO.findBookMarkWithNavigation(bookmarkId);
	}
}
