package service

import (
	"github.com/mageddo/go-logging"
	"context"
	"github.com/mageddo/bookmark-notes/dao"
	"fmt"
	"bytes"
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
	for _, b := range bookmarks {
		//fmt.Fprintf(&buff, "id=%d, name=%s, update=%s\n", b.Id, b.Name, b.Update)
		fmt.Fprintf(&buff, "%+v\n", b)
	}
	return buff.String(), err
}

func NewSiteMapService(ctx context.Context) SiteMapService {
	return SiteMapService{logging.NewLog(ctx), dao.NewBookmarkDAO(ctx) }
}
