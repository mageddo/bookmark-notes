package dao

import (
	"bk-api/entity"
	"database/sql"
)

type SettingsDAOSQLite struct {
	}


func (d *SettingsDAOSQLite) FindByKey(key string) *entity.SettingEntity {
	return nil
}

func (d *SettingsDAOSQLite) Save(tx *sql.Tx, settingEntity *entity.SettingEntity) error {
	return nil
}

func NewSettingsDAO() *SettingsDAOSQLite {
	return &SettingsDAOSQLite{}
}
