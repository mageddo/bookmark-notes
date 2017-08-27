package service

import (
	"github.com/mageddo/go-logging"
	"context"
	"github.com/mageddo/bookmark-notes/dao"
	"encoding/json"
	"github.com/mageddo/bookmark-notes/entity"
	"github.com/mageddo/bookmark-notes/util/date"
	"io"
)

type SiteMapService struct {
	logger logging.Log
	bookmarkDAO dao.BookmarkDAO
}

func (s *SiteMapService) LoadSiteMap(w io.Writer) (error) {
	bookmarks, err := s.bookmarkDAO.LoadSiteMap()
	if err != nil {
		return err
	}
	jsonWriter := json.NewEncoder(w)
	for _, b := range bookmarks {

		type BookmarkVO entity.BookmarkEntity
		jsonWriter.Encode(&struct {
			*BookmarkVO
			Update *string `json:"update"`
		}{
			(*BookmarkVO)(&b),
			date.GetFormattedDate(b.Update),
		})

	}
	return err
}

func NewSiteMapService(ctx context.Context) SiteMapService {
	return SiteMapService{logging.NewLog(ctx), dao.NewBookmarkDAO(ctx) }
}

