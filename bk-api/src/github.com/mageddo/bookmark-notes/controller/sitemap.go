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
		fmt.Fprintf(res, "%s", sc.LoadSiteMap())
	})

}
