package entity

import (
	"time"
)

type BookmarkEntity struct {
	Id int `json:"id"`
	Name *string `json:"name"`
	Update *time.Time `json:"update"`
}
