package dao

import (
	"github.com/mageddo/go-logging"
	"context"
)

type UtilsDAO interface {
	Exec(sql string)
}

func NewUtilsDAO(ctx context.Context) *UtilsDAOSQLite {
	return &UtilsDAOSQLite{logging.NewLog(ctx)}
}

