package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;

import java.util.List;

public interface TagDAO {
	List<TagV1Res> findTags();
}
