httpmap helps you map yours endpoints with a low overhead, it's just a wrapper that makes your work easier

### Examples

```go
Get("/users", func(ctx context.Context, w http.ResponseWriter, r *http.Request){
	...
})
Post("/users", func(ctx context.Context, w http.ResponseWriter, r *http.Request){
	...
})
Put("/users", func(ctx context.Context, w http.ResponseWriter, r *http.Request){
	...
})
``` 

### Installing new deps 

	go get -v github.com/golang/dep/cmd/dep && dep ensure -v

