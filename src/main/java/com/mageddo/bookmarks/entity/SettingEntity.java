package com.mageddo.bookmarks.entity;

import java.time.LocalDateTime;

public class SettingEntity {

	private String value;
	private String key;
	private LocalDateTime update;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public LocalDateTime getUpdate() {
		return update;
	}

	public SettingEntity setUpdate(LocalDateTime update) {
		this.update = update;
		return this;
	}
}
