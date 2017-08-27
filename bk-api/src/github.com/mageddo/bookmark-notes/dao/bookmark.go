package dao

import (
	"context"
	"github.com/mageddo/go-logging"
	"github.com/mageddo/bookmark-notes/entity"
)

type BookmarkDAO interface {
	LoadSiteMap() ([]entity.BookmarkEntity, error)
	GetBookmarks(offset, quantity int) ([]entity.BookmarkEntity, int, error)
	GetBookmarksByTagSlug(slug string, offset, quantity int) ([]entity.BookmarkEntity, int, error)
}

func NewBookmarkDAO(ctx context.Context) *BookmarkDAOSQLite {
	return &BookmarkDAOSQLite{logging.NewLog(ctx)}
}
