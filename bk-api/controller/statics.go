package controller

import (
	"net/http"
	"github.com/mageddo/go-logging"
	"bk-api/utils"
)

func init(){

	http.HandleFunc("/static/", func(res http.ResponseWriter, req *http.Request){
		staticPath := utils.GetPath("/")
		logging.Infof("urlPath=%s", req.URL.Path)
		hd := http.FileServer(http.Dir(staticPath))
		hd.ServeHTTP(res, req)
	})
}
