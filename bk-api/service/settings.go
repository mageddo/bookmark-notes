package service

import (
	"bk-api/dao"
	"bk-api/entity"
	"bk-api/db"
	"database/sql"
	"context"
)

type SettingsService struct {
	settingsDAO dao.SettingsDAO
}

func (s *SettingsService) GetSetting(key string) (*entity.SettingEntity, error){
	return s.settingsDAO.FindByKey(key)
}

func (s *SettingsService) FindAll() (*[]entity.SettingEntity, error){
	return s.settingsDAO.FindAll()
}

func (s *SettingsService) FindAllAsMap() (map[string]*entity.SettingEntity, error){
	rows, err := s.settingsDAO.FindAll()
	if err != nil {
		return nil, err
	}
	m := make(map[string]*entity.SettingEntity)
	for _, v := range *rows {
		m[v.Key] = &v
	}
	return m, nil
}

func (dao *SettingsService) SaveSetting(ctx context.Context, setting *entity.SettingEntity) error {
	return db.Execute(func(tx *sql.Tx) error {
		return dao.settingsDAO.Save(tx, setting)
	}, db.GetConn(), ctx)

}

func NewSettingsService() *SettingsService {
	return &SettingsService{ dao.NewSettingsDAO()}
}

