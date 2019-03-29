package com.mageddo.bookmarks.dao;

import com.mageddo.bookmarks.entity.SettingEntity;

import java.util.List;

public interface SettingsDAO {

	List<SettingEntity> findAll();

	SettingEntity find(String key);

	void patch(SettingEntity settingEntity);
}
