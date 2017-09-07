package entity

import (
	"time"
)

type Visibility int

const (
	PRIVATE Visibility = 0
	PUBLIC Visibility = 1
)

type BookmarkEntity struct {
	Id int `json:"id"`
	Name *string `json:"name"`
	Update *time.Time `json:"update,omitempty"`
	Visibility *Visibility `json:"visibility,omitempty"`
	HTML string `json:"html"`
	Link string `json:"link,omitempty"`
}


func NewBookmarkWithNameAndVisibility(name string, visibility Visibility) *BookmarkEntity {
	return &BookmarkEntity{Name: &name, Visibility: &visibility}
}
