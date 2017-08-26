package entity

import (
	"time"
	"database/sql"
)

type BookmarkEntity struct {
	Id int
	Name sql.NullString
	Update time.Time
}
