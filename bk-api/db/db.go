package db

import (
	_ "github.com/mattn/go-sqlite3"
	"database/sql"
	"bk-api/utils"
	"bk-api/env"
	"context"
)

var readOnlyDB *sql.DB
var db *sql.DB

func init() {
	var err error
	readOnlyDB, err = sql.Open("sqlite3", utils.GetConfig().DatabaseURL)
	readOnlyDB.SetMaxOpenConns(utils.GetConfig().Connections)
	if (err != nil) {
		panic(err)
	}

	db, err = sql.Open("sqlite3", utils.GetConfig().DatabaseURL)
	db.SetMaxOpenConns(1)
	if (err != nil) {
		panic(err)
	}
}

// Read only connection
func GetROConn() *sql.DB {
	return readOnlyDB
}

func GetConn() *sql.DB {
	if env.IsTestProfile() {
		return readOnlyDB
	}
	return db
}


func Execute(fn func(tx *sql.Tx) error, db *sql.DB, ctx context.Context, optsArr ...*sql.TxOptions) error {

	var opts *sql.TxOptions = nil
	if len(optsArr) != 0 {
		opts = optsArr[0]
	}
	tx, err := db.BeginTx(ctx, opts)
	if err != nil {
		return err
	}
	err = fn(tx)
	if err != nil {
		tx.Rollback()
		return err
	}
	tx.Commit()
	return nil
}
