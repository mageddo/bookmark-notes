package dao

import (
	"bk-api/entity"
	"database/sql"
)

type SettingsDAO interface {
	Save(tx *sql.Tx, settingEntity *entity.SettingEntity) error
	FindByKey(key string) *entity.SettingEntity
}
