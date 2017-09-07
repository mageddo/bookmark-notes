package controller

import (
	"testing"
	"github.com/stretchr/testify/assert"
	"bk-api/test"
	"bk-api/dao"
	"github.com/mageddo/go-logging"
	"bk-api/entity"
	"strings"
)

// /api/v1.0/sitemap
func TestGetV1_0Success(t *testing.T){

	expectedXML := strings.Replace(`<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
	<url>
		<loc>/bookmark/1/x</loc>

		<changefreq>weekly</changefreq>
		<priority>1</priority>
	</url></urlset>
`, "\t", "", -1)

	ctx := logging.NewContext()
	test.BuildDatabase()

	dao.NewBookmarkDAO(ctx).SaveBookmark(entity.NewBookmarkWithNameAndVisibility("X", entity.PUBLIC))

	resp, c, err := test.NewReq("GET", "/api/v1.0/sitemap")

	assert.Nil(t, err)
	assert.Equal(t, 200, c)

	resp = strings.Replace(resp, "\t", "", -1)

	assert.Equal(t, len(expectedXML), len(resp))
	assert.Equal(t, expectedXML, resp)

}
