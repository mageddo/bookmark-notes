package dao

import (
	"context"
	"github.com/mageddo/go-logging"
	"bk-api/entity"
)

type BookmarkDAO interface {
	LoadSiteMap() ([]entity.BookmarkEntity, error)
	GetBookmarks(offset, quantity int) ([]entity.BookmarkEntity, int, error)
	GetBookmarksByTagSlug(slug string, offset, quantity int) ([]entity.BookmarkEntity, int, error)
	GetBookmarksByNameOrHTML(query string, offset, quantity int) ([]entity.BookmarkEntity, int, error)
}

func NewBookmarkDAO(ctx context.Context) *BookmarkDAOSQLite {
	return &BookmarkDAOSQLite{logging.NewLog(ctx)}
}
