package service

import (
	"bk-api/dao"
	"bk-api/entity"
	"bk-api/db"
	"database/sql"
	"context"
	"time"
	"github.com/mageddo/go-logging"
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

func (s *SettingsService) UpdateValue(settings *[]entity.SettingEntity) (error){
	for _, setting := range *settings {
		t := time.Now()
		setting.UpdateDate = &t
		if err := s.SaveSetting(context.Background(), &setting); err != nil {
			logging.Errorf("status=failed-save, setting=%s", setting.Key, err)
			return err
		}
	}
	return nil
}

func (s *SettingsService) FindAllAsMap() (map[string]entity.SettingEntity, error){
	rows, err := s.settingsDAO.FindAll()
	if err != nil {
		return nil, err
	}
	m := make(map[string]entity.SettingEntity)
	for _, v := range *rows {
		m[v.Key] = v
	}
	return m, nil
}

func (dao *SettingsService) SaveSetting(ctx context.Context, setting *entity.SettingEntity) error {
	return db.Execute(func(tx *sql.Tx) error {
		affected, err := dao.settingsDAO.Save(tx, setting)
		logging.Infof("status=complete, affected=%d, err=%v", affected, err)
		return err
	}, db.GetConn(), ctx)

}

func NewSettingsService() *SettingsService {
	return &SettingsService{ dao.NewSettingsDAO()}
}

