package utils

import (
	"os"
	"strings"
)

func GetProfile() string {
	mode := os.Getenv("PROFILE")
	if len(mode) == 0 {
		mode = "dev"
	}
	return strings.ToLower(mode)
}

func IsTestProfile() bool {
	return GetProfile() == "test"
}
