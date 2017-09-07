package controller

import (
	"net/http"
	"context"
	"bk-api/service"
	"github.com/mageddo/go-logging"
)

func init(){
	Get("/api/v1.0/sitemap", func(ctx context.Context, w http.ResponseWriter, r *http.Request){

		sc := service.NewSiteMapService(ctx)
		logger := logging.NewLog(ctx)

		err := sc.LoadSiteMap(w, r.URL.Query().Get("url"))
		if err != nil {
			logger.Errorf("status=could-not-load, err=%+v", err)
			BadRequest(w, "Could not load sitemap")
		}

	})
}
