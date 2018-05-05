package entity

import "time"

type SettingEntity struct {
	Key string
	Value string
	update *time.Time
}
