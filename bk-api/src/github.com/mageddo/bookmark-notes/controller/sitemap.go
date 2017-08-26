package controller

import (
	"net/http"
	"fmt"
	"context"
	"github.com/mageddo/bookmark-notes/service"
)

func init(){
	Get("/sitemap.xml", func(ctx context.Context, res http.ResponseWriter, req *http.Request){
		sc := service.NewSiteMapService(ctx)
		sitemap, err := sc.LoadSiteMap()
		fmt.Fprintf(res, "sitemap=%s, err=%v", sitemap, err)
	})

}
