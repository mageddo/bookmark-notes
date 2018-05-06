package httpmap

import (
	"net/http"
	"encoding/json"
	"github.com/mageddo/go-logging"
	"context"
	"fmt"
	"os"
	"sync/atomic"
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

func Patch(path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	handle(PATCH, path, fn)
}

var m = make(map[string]func(context.Context, http.ResponseWriter, *http.Request))
func handle(method Method, path string, fn func(context.Context, http.ResponseWriter, *http.Request)) {
	key := getKey(string(method), path)
	if _, ok := m[key]; ok {
		logging.Errorf("status-already-registered, key=%s", key)
		os.Exit(-1)
	}
	logging.Infof("status=registering, path=%s", key)
	m[key] = fn

	if _, ok := m[path]; ok {
		return
	}

	http.HandleFunc(path, func(w http.ResponseWriter, r *http.Request) {
		fn, found := m[getKey(r.Method, r.URL.Path)]
		logging.Debugf("method=%s, path=%s, found=%t", r.Method, r.URL.Path, found)
		if found {
			fn(NextId(r.Context()), w, r)
		} else {
			http.NotFound(w, r)
		}
	})
	m[path] = nil
}

func getKey(m string, path string) string {
	return fmt.Sprintf("%s %s", m, path)
}

var counter int64 = 1
func NextId(ctx context.Context) context.Context {
	return context.WithValue(ctx, "ID", atomic.AddInt64(&counter, 1))
}
