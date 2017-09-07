package dao

import (
	"context"
	"github.com/mageddo/go-logging"
	"bk-api/entity"
	"database/sql"
)

type BookmarkDAO interface {
	LoadSiteMap() ([]entity.BookmarkEntity, error)
	GetBookmarks(offset, quantity int) ([]entity.BookmarkEntity, int, error)
	GetBookmarksByTagSlug(slug string, offset, quantity int) ([]entity.BookmarkEntity, int, error)
	GetBookmarksByNameOrHTML(query string, offset, quantity int) ([]entity.BookmarkEntity, int, error)
	SaveBookmark(tx *sql.Tx, bookmark *entity.BookmarkEntity) error
}

func NewBookmarkDAO(ctx context.Context) *BookmarkDAOSQLite {
	return &BookmarkDAOSQLite{logging.NewLog(ctx)}
}
