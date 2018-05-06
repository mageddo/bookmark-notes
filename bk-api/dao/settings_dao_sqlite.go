package dao

import (
	"bk-api/entity"
	"database/sql"
	"bk-api/db"
	"github.com/mageddo/go-logging"
)

type SettingsDAOSQLite struct {
}

func (d *SettingsDAOSQLite) FindByKey(key string) (*entity.SettingEntity, error) {
	stm, err := db.GetROConn().Prepare(`
		SELECT
			NAM_PROPERTY, DES_VALUE, DAT_UPDATE 
		FROM SYSTEM_PROPERTY 
		WHERE NAM_PROPERTY = ?
	`)
	if err != nil {
		return nil, err
	}
	defer stm.Close()
	et := &entity.SettingEntity{}
	err = stm.QueryRow(key).Scan(&et.Key, &et.Value, &et.UpdateDate)
	logging.Infof("status=success, key=%s", key)
	return et, err
}


func (d *SettingsDAOSQLite) FindAll() (*[]entity.SettingEntity, error) {
	stm, err := db.GetROConn().Prepare(`
		SELECT
			NAM_PROPERTY, DES_VALUE, DAT_UPDATE 
		FROM SYSTEM_PROPERTY 
	`)
	if err != nil {
		return nil, err
	}
	defer stm.Close()
	rows, err := stm.Query()
	if err != nil {
		logging.Errorf("status=rows-error", err)
		return nil, err
	}
	defer rows.Close()

	items := new([]entity.SettingEntity)
	for ; rows.Next(); {
		et := entity.SettingEntity{}
		rows.Scan(&et.Key, &et.Value, &et.UpdateDate)
		*items = append(*items, et)
	}
	return items, nil
}

func (d *SettingsDAOSQLite) Save(tx *sql.Tx, settingEntity *entity.SettingEntity) (int64, error) {
	r, err := tx.Exec(`
		UPDATE SYSTEM_PROPERTY SET 
			DAT_UPDATE = ?
			DES_VALUE = ?
		WHERE NAM_PROPERTY = ?
	`, settingEntity.UpdateDate.Format("2006-01-02 15:04:05"), settingEntity.Value, settingEntity.Key)
	if err != nil {
		logging.Errorf("status=update-failed", err)
		return 0, err
	}
	qtd, _ := r.RowsAffected()
	return qtd, nil
}

func NewSettingsDAO() SettingsDAO {
	return &SettingsDAOSQLite{}
}
