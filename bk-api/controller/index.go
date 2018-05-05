package controller

import (
	"net/http"
	"encoding/json"
	"github.com/mageddo/go-logging"
	"context"
	"bk-api/utils"
)

type Method string
const (
	POST Method = "POST"
	GET Method = "GET"
	PUT Method = "PUT"
	PATCH Method = "PATCH"
	DELETE Method = "DELETE"
)

type Message struct {
	Code int `json:"code"`
	Message string `json:"message"`
}

func BadRequest(w http.ResponseWriter, msg string){
	RespMessage(w, 400, msg)
}

func RespMessage(w http.ResponseWriter, code int, msg string){
	w.WriteHeader(code)
	json.NewEncoder(w).Encode(Message{Code:code, Message:msg})
}


func Post(path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	handle(POST, path, fn)
}

func Get(path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	handle(GET, path, fn)
}

func Put(path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	handle(PUT, path, fn)
}

func Delete(path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	handle(DELETE, path, fn)
}

func handle(method Method, path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {

	http.HandleFunc(path, func(w http.ResponseWriter, r *http.Request) {

		found := r.Method == string(method)
		logging.Debugf("method=%s, path=%s, found=%t", r.Method, r.URL.Path, found)
		if found {
			fn(utils.NextId(r.Context()), w, r)
		} else {
			http.NotFound(w, r)
		}

	})
}


