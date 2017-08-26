package dao

import (
	"github.com/mageddo/bookmark-notes/db"
	"bytes"
	"github.com/mageddo/go-logging"
)

type BookmarkDAOSQLite struct {
	logger logging.Log
}

func (dao *BookmarkDAOSQLite) LoadSiteMap() (string, error) {

	conn := db.GetConn()

	//result, err := conn.Exec("UPDATE BOOKMARK SET NAM_BOOKMARK='from bk-api' WHERE IDT_BOOKMARK= 1")
	//if err != nil {
	//	return "", err
	//}
	//rowsAffected, _ := result.RowsAffected()
	//dao.logger.Debugf("updated=%d", rowsAffected)

	rows, err := conn.Query("SELECT NAM_BOOKMARK FROM BOOKMARK")
	if err != nil {
		return "", err
	}

	defer rows.Close()

	buff := bytes.Buffer{}
	for rows.Next() {
		var name string;
		rows.Scan(&name)

		buff.WriteString(name)
		buff.WriteString("\n")
	}

	//dao.logger.Debugf("open-conn=%d", conn.Stats().OpenConnections)

	return buff.String(), nil
}
