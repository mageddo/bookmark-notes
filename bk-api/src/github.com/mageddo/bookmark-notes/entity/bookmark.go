package entity

import (
	"time"
)

type Visibility int

type BookmarkEntity struct {
	Id int `json:"id"`
	Name *string `json:"name"`
	Update *time.Time `json:"update"`
	Visibility Visibility `json:"visibility"`
	HTML string `json:"html"`
}
