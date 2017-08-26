package dao

type BookmarkDAO interface {
	LoadSiteMap() (string, error)

}
