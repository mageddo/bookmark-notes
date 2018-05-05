package main

import (
	_ "bk-api/controller/api"
	_ "bk-api/controller/app"
	_ "bk-api/log"
	"net/http"
	"github.com/mageddo/go-logging"
)

func main(){

	logger := logging.NewLog(logging.NewContext())
	logger.Debugf("status=starting...")
	if err := http.ListenAndServe(":3131", nil); err != nil {
		logger.Errorf("error=%v", err)
	}
}

