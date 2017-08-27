package utils

import (
	"os"
	"strings"
)

func GetProfile() string {
	mode := os.Getenv("PROFILE")
	if len(mode) == 0 {
		mode = "DEV"
	}
	return strings.ToUpper(mode)
}
