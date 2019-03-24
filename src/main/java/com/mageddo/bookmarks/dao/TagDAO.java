package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.entity.TagEntity;

import java.util.List;

public interface TagDAO {

	List<TagV1Res> findTags();

	List<TagEntity> findTags(long bookmarkId);
}
