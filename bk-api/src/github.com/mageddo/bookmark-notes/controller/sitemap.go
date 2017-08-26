package controller

import (
	"net/http"
	"fmt"
	"context"
	"github.com/mageddo/bookmark-notes/service"
	"github.com/mageddo/go-logging"
)

func init(){
	Get("/sitemap.xml", func(ctx context.Context, res http.ResponseWriter, req *http.Request){
		sc := service.NewSiteMapService(ctx)
		logger := logging.NewLog(ctx)

		sitemap, err := sc.LoadSiteMap()
		if err != nil {
			logger.Errorf("status=could-not-load, err=%+v", err)
		}
		fmt.Fprintf(res, "sitemap=%s, err=%v", sitemap, err)
	})

}
