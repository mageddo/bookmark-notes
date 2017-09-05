package db

import (
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
)

var db *sql.DB

func init() {
	var err error
	//db, err = sql.Open("sqlite3", "/var/lib/mageddo/bookmarks-node/db/bookmarks.db?mode=ro")
	db, err = sql.Open("sqlite3", "/opt/bookmarks/db/bookmarks.db")
	db.SetMaxOpenConns(5)
	if (err != nil) {
		panic(err)
	}
}

func GetConn() *sql.DB {
	return db
}
