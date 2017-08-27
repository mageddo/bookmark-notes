package main

import (
	_ "github.com/mageddo/bookmark-notes/controller"
	_ "github.com/mageddo/bookmark-notes/log"
	"net/http"
)

func main(){

	http.ListenAndServe(":3131", nil)

}

