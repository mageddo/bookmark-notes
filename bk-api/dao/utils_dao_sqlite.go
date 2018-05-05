package dao

import (
	"github.com/mageddo/go-logging"
	"bk-api/db"
	"fmt"
)

type UtilsDAOSQLite struct {
}

func (dao *UtilsDAOSQLite) Exec(sql string, args ...string) error {
	_, err := db.GetConn().Exec(sql, args)
	return err
}

func (dao *UtilsDAOSQLite) CreateTables() error {
	logging.Info("status=start")

	_, err := db.GetConn().Exec(`CREATE TABLE IF NOT EXISTS TAG (
	IDT_TAG INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	NAM_TAG VARCHAR (100) NOT NULL,
	COD_SLUG VARCHAR (100) NOT NULL UNIQUE,
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
CREATE TABLE IF NOT EXISTS BOOKMARK (
	IDT_BOOKMARK INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL,
	NAM_BOOKMARK VARCHAR (500),
	DES_LINK VARCHAR (1000),
	DES_HTML TEXT,
	FLG_DELETED BOOLEAN NOT NULL,
	FLG_ARCHIVED BOOLEAN NOT NULL,
	NUM_VISIBILITY TINYINT NOT NULL,
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
CREATE TABLE IF NOT EXISTS TAG_BOOKMARK (
	IDT_TAG INTEGER NOT NULL REFERENCES TAG(IDT_TAG),
	IDT_BOOKMARK INTEGER NOT NULL REFERENCES BOOKMARK(IDT_BOOKMARK),
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME,
	PRIMARY KEY (IDT_TAG, IDT_BOOKMARK)
);
CREATE TABLE IF NOT EXISTS SYSTEM_PROPERTY (
	NAM_PROPERTY VARCHAR(25) UNIQUE NOT NULL,
	DES_VALUE VARCHAR(100),
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
`)
	logging.Infof("status=success, error=%v", err)
	return err
}

func (dao *UtilsDAOSQLite) TruncateTables() error {
	logging.Info("status=begin")

	conn := db.GetConn()
	tx , err := conn.Begin()
	if err != nil {
		logging.Errorf("error=cannot-open-tx, err=%v", err)
		return err
	}

	rows, err := tx.Query(`SELECT name FROM sqlite_master WHERE type='table'`);
	if err != nil {
		logging.Errorf("status=get-tables, err=%v", err)
		return err
	}
	defer rows.Close()

	for rows.Next() {

		var name string
		rows.Scan(&name)
		logging.Debugf("status=before, table=%s", name)
		_, err := tx.Exec(fmt.Sprintf("DELETE FROM %s", name))
		if err != nil {
			logging.Errorf("status=delete-rows, err=%v", err)
			return err
		}
		logging.Debugf("status=success, table=%s", name)
	}
	tx.Commit()

	logging.Info("status=success")
	return nil
}
