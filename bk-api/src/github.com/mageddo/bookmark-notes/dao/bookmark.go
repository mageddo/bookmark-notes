package dao

import (
	"context"
	"github.com/mageddo/go-logging"
)

type BookmarkDAO interface {
	LoadSiteMap() (string, error)
}

func NewBookmarkDAO(ctx context.Context) *BookmarkDAOSQLite {
	return &BookmarkDAOSQLite{logging.NewLog(ctx)}
}
