package com.mageddo.bookmarks.service;

import java.util.List;

import javax.inject.Singleton;

import com.mageddo.bookmarks.apiserver.res.TagV1Res;
import com.mageddo.bookmarks.dao.TagDAO;
import com.mageddo.bookmarks.entity.TagEntity;

@Singleton
public class TagService {

  private final TagDAO tagDAO;

  public TagService(TagDAO tagDAO) {
    this.tagDAO = tagDAO;
  }

  public List<TagV1Res> getTags() {
    return tagDAO.findTags();
  }

  public List<TagEntity> getTags(long bookmarkId) {
    return tagDAO.findTags(bookmarkId);
  }

  public List<TagEntity> findTags(String query) {
    return tagDAO.findTags(query);
  }
}
