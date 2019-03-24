package com.mageddo.bookmarks.service;

import com.mageddo.bookmarks.dao.SettingsDAO;
import com.mageddo.bookmarks.entity.SettingEntity;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class SettingsService {

	private final SettingsDAO settingsDAO;

	public SettingsService(SettingsDAO settingsDAO) {
		this.settingsDAO = settingsDAO;
	}

	public Map<String, String> findAllAsMap() {
		final List<SettingEntity> settings = settingsDAO.findAll();
		final Map<String, String> settingsMap = new LinkedHashMap<>();
		for (SettingEntity setting : settings) {
			settingsMap.put(setting.getKey(), setting.getValue());
		}
		return settingsMap;
	}

	public List<SettingEntity> findAll() {
		return settingsDAO.findAll();
	}

	public SettingEntity findSetting(String key) {
		return settingsDAO.find(key);
	}

	public void patch(SettingEntity settingEntity) {
		final SettingEntity dbSettingEntity = settingsDAO.find(settingEntity.getKey());
		dbSettingEntity.setValue(settingEntity.getValue());
		dbSettingEntity.setUpdate(LocalDateTime.now());
		settingsDAO.patch(dbSettingEntity);
	}

	public void patch(List<SettingEntity> settings) {
		for (SettingEntity setting : settings) {
			this.patch(setting);
		}
	}
}
