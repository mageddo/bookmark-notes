package controller

import (
	"net/http"
	log "github.com/mageddo/go-logging"
	"bk-api/utils"
)

func init(){

	http.HandleFunc("/static/", func(res http.ResponseWriter, req *http.Request){
		logger := log.NewLog(log.NewContext())

		staticPath := utils.GetPath("/")
		logger.Infof("urlPath=%s", req.URL.Path)
		hd := http.FileServer(http.Dir(staticPath))
		hd.ServeHTTP(res, req)
	})
}
