package dao

import (
	"github.com/mageddo/bookmark-notes/db"
	"bytes"
)

type BookmarkDAOSQLite struct {
}

func (dao *BookmarkDAOSQLite) LoadSiteMap() (string, error) {

	conn := db.GetConn()
	defer conn.Close()

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
	return buff.String(), nil
}
