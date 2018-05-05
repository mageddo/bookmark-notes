package main

import (
	_ "bk-api/controller/api"
	_ "bk-api/controller/app"
	_ "bk-api/log"
	"net/http"
	"github.com/mageddo/go-logging"
)

func main(){

	logging.Debugf("status=starting...")
	if err := http.ListenAndServe(":3131", nil); err != nil {
		logging.Errorf("error=%v", err)
	}
}

