package service

import (
	"github.com/mageddo/go-logging"
	"context"
)

type SiteMapService struct {
	logger logging.Log
}

func (s *SiteMapService) LoadSiteMap() string {
	s.logger.Debugf("status=begin")
	return "Hi!"
}

func NewSiteMapService(ctx context.Context) SiteMapService {
	return SiteMapService{logging.NewLog(ctx)}
}
