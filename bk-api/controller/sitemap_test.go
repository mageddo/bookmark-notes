package controller

import (
	"testing"
	_ "bk-api/test"
	"github.com/stretchr/testify/assert"
	"bk-api/test"
	"fmt"
)

// /api/v1.0/sitemap
func TestGetV1_0Success(t *testing.T){

	resp, c, err := test.NewReq("GET", "/api/v1.0/sitemap")
	fmt.Println(resp, c, err)
	assert.Equal(t, 200, c)
	assert.Equal(t, "x", resp)

}
