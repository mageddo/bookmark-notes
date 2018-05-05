package service

import (
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
	SEARCH_MIN_SIZE = 3
)

type BookmarkService struct {
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
	}, db.GetConn(), context.Background())

}

func NewBookmarkService() *BookmarkService {
	return &BookmarkService{dao.NewBookmarkDAO()}
}
