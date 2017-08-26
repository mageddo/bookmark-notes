package controller

import (
	"net/http"
	"fmt"
	"context"
)

func init(){
	Get("/sitemap.xml", func(ctx context.Context, res http.ResponseWriter, req *http.Request){
		fmt.Fprintf(res, "%s", "Hi!")
	})

}
