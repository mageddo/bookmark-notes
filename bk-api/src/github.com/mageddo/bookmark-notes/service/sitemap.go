package service

import (
	"github.com/mageddo/go-logging"
	"context"
	"github.com/mageddo/bookmark-notes/dao"
	"bytes"
	"encoding/json"
	"github.com/mageddo/bookmark-notes/entity"
)

type SiteMapService struct {
	logger logging.Log
	bookmarkDAO dao.BookmarkDAO
}

func (s *SiteMapService) LoadSiteMap() (string, error) {
	bookmarks, err := s.bookmarkDAO.LoadSiteMap()
	if err != nil {
		return "", err
	}
	buff := bytes.Buffer{}
	jsonWriter := json.NewEncoder(&buff)
	for _, b := range bookmarks {

		type BookmarkVO entity.BookmarkEntity
		jsonWriter.Encode(&struct {
			*BookmarkVO
			update string
		}{(*BookmarkVO)(&b), b.Update.Format("2006-01-02 15:04:05")})
		//&struct {
		//
		//	Name string
		//}{(*BookmarkVO)(&b), *b.Name}

	}
	return buff.String(), err
}

func NewSiteMapService(ctx context.Context) SiteMapService {
	return SiteMapService{logging.NewLog(ctx), dao.NewBookmarkDAO(ctx) }
}
