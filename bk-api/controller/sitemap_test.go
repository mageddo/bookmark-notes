package controller

import (
	"testing"
	"github.com/stretchr/testify/assert"
	"bk-api/test"
	"fmt"
	"bk-api/dao"
	"github.com/mageddo/go-logging"
	"bk-api/entity"
)

// /api/v1.0/sitemap
func TestGetV1_0Success(t *testing.T){

	ctx := logging.NewContext()
	test.BuildDatabase()

	name := "X"
	dao.NewBookmarkDAO(ctx).SaveBookmark(&entity.BookmarkEntity{Name: &name})

	resp, c, err := test.NewReq("GET", "/api/v1.0/sitemap")
	fmt.Println(resp, c, err)
	assert.Equal(t, 200, c)
	assert.Equal(t, "x", resp)

}
