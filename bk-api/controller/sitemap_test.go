package controller

import (
	"testing"
	"github.com/stretchr/testify/assert"
	"bk-api/test"
	"fmt"
)

// /api/v1.0/sitemap
func TestGetV1_0Success(t *testing.T){

	test.BuildDatabase()

	resp, c, err := test.NewReq("GET", "/api/v1.0/sitemap")
	fmt.Println(resp, c, err)
	assert.Equal(t, 200, c)
	assert.Equal(t, "x", resp)

}
