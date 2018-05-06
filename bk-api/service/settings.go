package service

import (
	"bk-api/dao"
	"bk-api/entity"
	"bk-api/db"
	"database/sql"
	"context"
	"time"
	"github.com/mageddo/go-logging"
	"errors"
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

func (s *SettingsService) UpdateValue(settings *[]entity.SettingEntity) (error) {
	err := db.Execute(func(tx *sql.Tx) error {
		for _, setting := range *settings {
			if len(setting.Value) > 100 {
				return errors.New("Value can't have more than 100 letters")
			}
			t := time.Now()
			setting.UpdateDate = &t
			affected, err := s.settingsDAO.Save(tx, &setting)
			if err != nil {
				logging.Errorf("status=failed-save, setting=%s", setting.Key, err)
				return err
			}
			logging.Infof("status=complete, affected=%d, err=%v", affected, err)
		}
		return nil
	}, db.GetConn(), context.Background())
	return err
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

