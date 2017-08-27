package service

import (
	"github.com/mageddo/go-logging"
	"github.com/mageddo/bookmark-notes/dao"
	"context"
	"github.com/mageddo/bookmark-notes/entity"
)

type BookmarkService struct {
	logger logging.Log
	bookmarkDAO dao.BookmarkDAO
}

func (s *BookmarkService) GetBookmarks(offset, quantity int) ([]entity.BookmarkEntity, int, error){
	return s.bookmarkDAO.GetBookmarks(offset, quantity)
}


func NewBookmarkService(ctx context.Context) *BookmarkService {
	return &BookmarkService{logging.NewLog(ctx), dao.NewBookmarkDAO(ctx)}
}
