package dao

import (
	"github.com/mageddo/bookmark-notes/db"
	"github.com/mageddo/go-logging"
	"github.com/mageddo/bookmark-notes/entity"
)

type BookmarkDAOSQLite struct {
	logger logging.Log
}

func (dao *BookmarkDAOSQLite) LoadSiteMap() ([]entity.BookmarkEntity, error) {

	conn := db.GetConn()

	//result, err := conn.Exec("UPDATE BOOKMARK SET NAM_BOOKMARK='from bk-api' WHERE IDT_BOOKMARK= 1")
	//if err != nil {
	//	return "", err
	//}
	//rowsAffected, _ := result.RowsAffected()
	//dao.logger.Debugf("updated=%d", rowsAffected)

	rows, err := conn.Query(`
		SELECT * FROM (
		SELECT IDT_BOOKMARK, NAM_BOOKMARK, DAT_UPDATE
		FROM BOOKMARK
		ORDER BY IDT_BOOKMARK DESC
	) LIMIT 0, 100000`)
	if err != nil {
		return nil, err
	}

	defer rows.Close()

	bookmarks := new([]entity.BookmarkEntity)
	for rows.Next() {
		var b = entity.BookmarkEntity{}
		rows.Scan(&b.Id, &b.Name, &b.Update)
		*bookmarks = append(*bookmarks, b)
	}

	dao.logger.Debugf("status=success, qtd=%d", len(*bookmarks))

	return *bookmarks, nil
}
