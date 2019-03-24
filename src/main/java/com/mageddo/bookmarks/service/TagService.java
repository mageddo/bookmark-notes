package com.mageddo.bookmarks.service;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.dao.TagDAO;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TagService {

	private final TagDAO tagDAO;

	public TagService(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	public List<TagV1Res> getTags() {
		return tagDAO.findTags();
	}
}
