package controller

import (
	"testing"
	"github.com/mageddo/go-logging"
	"bk-api/test"
	"bk-api/service"
	"bk-api/entity"
	"github.com/stretchr/testify/assert"
)

func TestGetV1_0ListBookmarksNoOffsetError(t *testing.T){

	test.BuildDatabase()

	nowOffsetMsg := `{"code":400,"message":"Please pass a valid offset and quantity"}
`
	resp, c, err := test.NewReq("GET", BOOKMARK_V1)

	assert.Nil(t, err)
	assert.Equal(t, 400, c)

	assert.Equal(t, nowOffsetMsg, resp)

}

func TestGetV1_0ListBookmarks(t *testing.T){

	ctx := logging.NewContext()
	test.BuildDatabase()

	expectedBookmarks := "[{\"id\":1,\"name\":\"X\",\"visibility\":1,\"html\":\"\",\"length\":3}]"

	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X2", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X3", entity.PRIVATE))

	resp, c, err := test.NewReq("GET", BOOKMARK_V1 + "?from=0&quantity=1")

	assert.Nil(t, err)
	assert.Equal(t, 200, c)

	resp = regex.ReplaceAllString(resp, "")

	assert.Equal(t, len(expectedBookmarks), len(resp))
	assert.Equal(t, expectedBookmarks, resp)

}

func TestGetV1_0ListBookmarksValidateFromSuccess(t *testing.T){

	ctx := logging.NewContext()
	test.BuildDatabase()

	expectedBookmarks := "[{\"id\":2,\"name\":\"X2\",\"visibility\":1,\"html\":\"\",\"length\":3},{\"id\":3,\"name\":\"X3\",\"visibility\":0,\"html\":\"\",\"length\":3}]"

	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X2", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X3", entity.PRIVATE))

	resp, c, err := test.NewReq("GET", BOOKMARK_V1 + "?from=1&quantity=2")

	assert.Nil(t, err)
	assert.Equal(t, 200, c)

	resp = regex.ReplaceAllString(resp, "")

	assert.Equal(t, len(expectedBookmarks), len(resp))
	assert.Equal(t, expectedBookmarks, resp)

}

func TestGetV1_0ListBookmarksSearchSuccess(t *testing.T){

	ctx := logging.NewContext()
	test.BuildDatabase()

	expectedBookmarks := `[{"id":2,"name":"Android 7.0 was released","html":"","length":2},{"id":3,"name":"Separate your software release by major, minor and patch","html":"","length":2}]`

	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Google is the most popular search engine site", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Android 7.0 was released", entity.PUBLIC))
	service.NewBookmarkService(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("Separate your software release by major, minor and patch", entity.PRIVATE))

	resp, c, err := test.NewReq("GET", BOOKMARK_V1 + "?from=0&quantity=3&query=release")

	assert.Nil(t, err)
	assert.Equal(t, 200, c)

	resp = regex.ReplaceAllString(resp, "")

	assert.Equal(t, len(expectedBookmarks), len(resp))
	assert.Equal(t, expectedBookmarks, resp)

}


