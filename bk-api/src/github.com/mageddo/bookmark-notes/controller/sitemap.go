package controller

import (
	"net/http"
	"context"
	"github.com/mageddo/bookmark-notes/service"
	"github.com/mageddo/go-logging"
)

func init(){
	Get("/sitemap.xml", func(ctx context.Context, w http.ResponseWriter, r *http.Request){
		sc := service.NewSiteMapService(ctx)
		logger := logging.NewLog(ctx)

		err := sc.LoadSiteMap(w)
		if err != nil {
			logger.Errorf("status=could-not-load, err=%+v", err)
		}

	})

}
