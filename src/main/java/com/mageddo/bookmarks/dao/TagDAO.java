package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.entity.TagEntity;
import com.mageddo.bookmarks.utils.Tags;

import java.util.List;

public interface TagDAO {

	List<TagV1Res> findTags();

	List<TagEntity> findTags(long bookmarkId);

	List<TagEntity> findTags(String query);

	void createIgnoreDuplicates(Tags.Tag tag);

	TagEntity findTag(String slug);
}
