package test

import (
	_ "bk-api/log"
	"net/http/httptest"
	"io"
	"net/http"
	"log"
	"bytes"
	"os"
)
var Server *httptest.Server

func init (){
	Server = httptest.NewServer(nil)
}

func NewReq(method, path string, rds ...io.Reader) (string, int, error) {

	var r io.Reader = nil
	if len(rds) > 0{
		r = rds[0]
	}

	req, err := http.NewRequest(method, Server.URL + path, r)
	if err != nil {
		log.Fatal(err)
	}

	res, err := http.DefaultClient.Do(req)
	if err != nil {
		return "", -1, err
	}

	buff := bytes.NewBufferString("")
	if _, err := io.Copy(os.Stdout, res.Body); err != nil {
		return "", -1, err
	}
	return buff.String(), res.StatusCode, nil
}
