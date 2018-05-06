package entity

import "time"

type SettingEntity struct {
	Key string `json:"key"`
	Value string `json:"value"`
	UpdateDate *time.Time `json:"updateDate"`
}
