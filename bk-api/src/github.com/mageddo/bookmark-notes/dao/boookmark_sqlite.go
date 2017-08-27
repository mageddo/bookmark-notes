package dao

import (
	"github.com/mageddo/bookmark-notes/db"
	"github.com/mageddo/go-logging"
	"github.com/mageddo/bookmark-notes/entity"
	"time"
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

func (dao *BookmarkDAOSQLite) GetBookmarks(offset, quantity int) ([]entity.BookmarkEntity, int, error) {

	timer := time.Now()
	conn := db.GetConn()
	stm, err := conn.Prepare(`WITH LIST AS (
		SELECT * FROM bookmark
	)
	SELECT idt_bookmark, nam_bookmark, num_visibility, SUBSTR(des_html, 0, 160), (SELECT COUNT(idt_bookmark) FROM LIST)
	FROM LIST WHERE flg_deleted=0 LIMIT ?, ?`)
	if err != nil {
		dao.logger.Errorf("status=prepare-query, err=%v", err)
		return nil, -1, err
	}
	defer stm.Close()
	rows, err := stm.Query(offset, quantity)
	if err != nil {
		dao.logger.Errorf("status=execute-query, err=%v", err)
		return nil, -1, err
	}
	defer rows.Close()
	var length int
	bookmarks := new([]entity.BookmarkEntity)
	for rows.Next() {
		b := entity.BookmarkEntity{}
		rows.Scan(&b.Id, &b.Name, &b.Visibility, &b.HTML, &length)
		*bookmarks = append(*bookmarks, b)
	}
	dao.logger.Infof("status=success, offset=%d, quantity=%d, length=%d, bookmarks=%d, time=%d", offset, quantity, length,
		len(*bookmarks), time.Now().UnixNano() - timer.UnixNano())
	return *bookmarks, length, nil

}

func (dao *BookmarkDAOSQLite) GetBookmarksByTagSlug(slug string, offset, quantity int) ([]entity.BookmarkEntity, int, error) {

	dao.logger.Infof("status=prepare, slug=%s, offset=%d, quantity=%d", slug, offset, quantity)
	conn := db.GetConn()
	stm, err := conn.Prepare(`WITH FILTER AS (
		SELECT DISTINCT B.* FROM TAG_BOOKMARK TB
			INNER JOIN BOOKMARK B ON B.IDT_BOOKMARK = TB.IDT_BOOKMARK
			WHERE IDT_TAG IN (
				SELECT T.IDT_TAG FROM TAG T
					WHERE T.COD_SLUG = ?
			)
			AND B.FLG_DELETED = 0
	)
	SELECT idt_bookmark, nam_bookmark,
		des_link, (SELECT COUNT(idt_bookmark) FROM FILTER) AS LENGTH,
		SUBSTR(des_html, 0, 160) as HTML
	FROM FILTER LIMIT ?, ?`)

	if err != nil {
		dao.logger.Errorf("status=prepare, slug=%s, err=%v", slug, err)
		return nil, -1, err
	}
	defer stm.Close()

	rows, err := stm.Query(slug, offset, quantity)
	if err != nil {
		dao.logger.Errorf("status=query, slug=%s, err=%v", slug, err)
		return nil, -1, err
	}
	defer rows.Close()

	var length int
	bookmarks := new([]entity.BookmarkEntity)
	for rows.Next() {
		b := entity.BookmarkEntity{}
		rows.Scan(&b.Id, &b.Name, &b.Link, &length, &b.HTML)
		*bookmarks = append(*bookmarks, b)
	}
	dao.logger.Infof("status=success, slug=%s, offset=%d, quantity=%d, length=%d", slug, offset, quantity, len(*bookmarks))
	return *bookmarks, length, nil
}
