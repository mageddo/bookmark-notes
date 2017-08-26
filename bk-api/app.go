package main

import (
	"net/http"
	_ "github.com/mageddo/bookmark-notes/controller"
	"fmt"
)

func main(){

	http.HandleFunc("/x", func(w http.ResponseWriter, r *http.Request) {
		fmt.Println("ok")
	})

	http.ListenAndServe(":3131", nil)

}

