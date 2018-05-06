package controller

import (
	"net/http"
	"context"
	"bk-api/service"
	"github.com/mageddo/go-logging"
	. "github.com/mageddo/go-httpmap"
)

func init(){
	Get("/api/v1.0/sitemap", func(ctx context.Context, w http.ResponseWriter, r *http.Request){

		sc := service.NewSiteMapService()
		err := sc.LoadSiteMap(w, r.URL.Query().Get("url"))
		if err != nil {
			logging.Errorf("status=could-not-load, err=%+v", err)
			BadRequest(w, "Could not load sitemap")
		}
	})
}
