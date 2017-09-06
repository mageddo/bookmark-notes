package main

import (
	"net/http/httptest"
	"net/http"
	"fmt"
	"io"
	"os"
	"log"
)

func main(){

	http.HandleFunc("/x", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintln(w, "hi")
	})

	s := httptest.NewServer(nil)

	req, err := http.NewRequest("GET", s.URL + "/x", nil)
	if err != nil {
		log.Fatal(err)
	}
	
	res, err := http.DefaultClient.Do(req)
	fmt.Printf("res=%v, err=%v\n", res, err)
	if err != nil {
		log.Panic(err)
	}

	if _, err := io.Copy(os.Stdout, res.Body); err != nil {
		log.Fatal(err)
	}

}
