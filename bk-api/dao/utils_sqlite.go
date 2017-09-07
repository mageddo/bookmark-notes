package dao

import (
	"github.com/mageddo/go-logging"
	"bk-api/db"
	"fmt"
)

type UtilsDAOSQLite struct {
	logger logging.Log
}

func (dao *UtilsDAOSQLite) Exec(sql string, args ...string) error {
	_, err := db.GetConn().Exec(sql, args)
	return err
}

func (dao *UtilsDAOSQLite) CreateTables() error {
	dao.logger.Info("status=start")
	_, err := db.GetConn().Exec(`CREATE TABLE TAG (
	IDT_TAG INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	NAM_TAG VARCHAR (100) NOT NULL,
	COD_SLUG VARCHAR (100) NOT NULL UNIQUE,
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
CREATE TABLE BOOKMARK (
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
CREATE TABLE TAG_BOOKMARK (
	IDT_TAG INTEGER NOT NULL REFERENCES TAG(IDT_TAG),
	IDT_BOOKMARK INTEGER NOT NULL REFERENCES BOOKMARK(IDT_BOOKMARK),
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME,
	PRIMARY KEY (IDT_TAG, IDT_BOOKMARK)
);
CREATE TABLE SYSTEM_PROPERTY (
	NAM_PROPERTY VARCHAR(25) UNIQUE NOT NULL,
	DES_VALUE VARCHAR(100),
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
`)
	dao.logger.Infof("status=success, error=%v", err)
	return err
}

func (dao *UtilsDAOSQLite) TruncateTables() error {
	dao.logger.Info("status=begin")
	conn := db.GetConn()
	rows, err := conn.Query(`SELECT name FROM sqlite_master WHERE type='table'`);
	if err != nil {
		dao.logger.Errorf("status=get-tables, err=%v", err)
		return err
	}
	defer rows.Close()

	for rows.Next() {

		var name string
		rows.Scan(&name)
		_, err := conn.Exec(fmt.Sprintf("SELECT * FROM %s", name))
		if err != nil {
			dao.logger.Errorf("status=delete-rows, err=%v", err)
			return err
		}
		dao.logger.Info("table=%s", name)

	}
	dao.logger.Info("status=success")
	return nil
}
