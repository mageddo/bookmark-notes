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

	rows, err := conn.Query(`
		SELECT * FROM (
		SELECT IDT_BOOKMARK, NAM_BOOKMARK, DAT_UPDATE
		FROM BOOKMARK
		WHERE NUM_VISIBILITY = 1
		AND FLG_DELETED = 0
		AND FLG_ARCHIVED = 0
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
