package service

import (
	"github.com/mageddo/go-logging"
	"bk-api/dao"
	"context"
	"bk-api/entity"
	"strings"
	"fmt"
	"bk-api/errors"
	"database/sql"
	"bk-api/db"
)

const (
	SEARCH_MIN_SIZE = 4
)

type BookmarkService struct {
	ctx context.Context
	logger logging.Log
	bookmarkDAO dao.BookmarkDAO
}

func (s *BookmarkService) GetBookmarks(offset, quantity int, tag, query string) ([]entity.BookmarkEntity, int, error){

	// clearing the data
	tag = strings.TrimSpace(tag)
	query = strings.TrimSpace(query)

	if tag != "" {
		return s.bookmarkDAO.GetBookmarksByTagSlug(tag, offset, quantity)
	} else if query != "" {
		if len(query) < SEARCH_MIN_SIZE {
			return nil, -1, errors.NewServiceError(fmt.Sprintf("Search text must have %d characters at least", SEARCH_MIN_SIZE))
		}
		return s.bookmarkDAO.GetBookmarksByNameOrHTML(query, offset, quantity);
	}
	return s.bookmarkDAO.GetBookmarks(offset, quantity)
}

func (dao *BookmarkService) SaveBookmark(bookmark *entity.BookmarkEntity) error {
	return db.Execute(func(tx *sql.Tx) error {
		return dao.bookmarkDAO.SaveBookmark(tx, bookmark)
	}, db.GetConn(), dao.ctx)

}

func NewBookmarkService(ctx context.Context) *BookmarkService {
	return &BookmarkService{ctx, logging.NewLog(ctx), dao.NewBookmarkDAO(ctx)}
}
