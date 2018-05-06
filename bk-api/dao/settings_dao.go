package dao

import (
	"bk-api/entity"
	"database/sql"
)

type SettingsDAO interface {
	Save(tx *sql.Tx, settingEntity *entity.SettingEntity) (int64, error)
	FindByKey(key string) (*entity.SettingEntity, error)
	FindAll() (*[]entity.SettingEntity, error)
}
