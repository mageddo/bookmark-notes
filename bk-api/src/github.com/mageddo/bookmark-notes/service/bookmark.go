package service

import (
	"github.com/mageddo/go-logging"
	"github.com/mageddo/bookmark-notes/dao"
	"context"
	"github.com/mageddo/bookmark-notes/entity"
	"strings"
	"fmt"
	"github.com/mageddo/bookmark-notes/errors"
)

const (
	SEARCH_MIN_SIZE = 4
)

type BookmarkService struct {
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


func NewBookmarkService(ctx context.Context) *BookmarkService {
	return &BookmarkService{logging.NewLog(ctx), dao.NewBookmarkDAO(ctx)}
}
