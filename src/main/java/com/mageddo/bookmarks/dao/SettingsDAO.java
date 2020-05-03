package com.mageddo.bookmarks.dao;

import java.util.List;

import com.mageddo.bookmarks.entity.SettingEntity;

public interface SettingsDAO {

  List<SettingEntity> findAll();

  SettingEntity find(String key);

  void patch(SettingEntity settingEntity);
}
