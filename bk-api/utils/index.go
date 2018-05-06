package utils

import (
	"context"
	"sync/atomic"
)

var counter int64 = 1
func NextId(ctx context.Context) context.Context {
	return context.WithValue(ctx, "ID", atomic.AddInt64(&counter, 1))
}
