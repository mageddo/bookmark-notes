package test

import (
	_ "bk-api/controller"
	_ "bk-api/log"
	"net/http/httptest"
)
var Server *httptest.Server

func init (){
	Server = httptest.NewServer(nil)
}
