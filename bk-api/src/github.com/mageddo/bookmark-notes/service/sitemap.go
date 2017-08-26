package service

import (
	"github.com/mageddo/go-logging"
	"context"
	"github.com/mageddo/bookmark-notes/dao"
)

type SiteMapService struct {
	logger logging.Log
	bookmarkDAO dao.BookmarkDAO
}

func (s *SiteMapService) LoadSiteMap() (string, error) {
	s.logger.Debugf("status=begin")
	return s.bookmarkDAO.LoadSiteMap()
}

func NewSiteMapService(ctx context.Context) SiteMapService {
	return SiteMapService{logging.NewLog(ctx),
		&dao.BookmarkDAOSQLite{},
	}
}
