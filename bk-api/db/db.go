package db

import (
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
	"bk-api/utils"
)

var db *sql.DB

func init() {
	var err error
	//db, err = sql.Open("sqlite3", "/var/lib/mageddo/bookmarks-node/db/bookmarks.db?mode=ro")
	
	db, err = sql.Open("sqlite3", utils.GetConfig().DatabaseURL)
	db.SetMaxOpenConns(5)
	if (err != nil) {
		panic(err)
	}
}

func GetConn() *sql.DB {
	return db
}
