package env

import (
	"os"
	"strings"
	"flag"
)

func GetProfile() string {
	mode := os.Getenv("PROFILE")
	if len(mode) == 0 {
		if flag.Lookup("test.v") != nil {
			mode = "test"
		}else {
			mode = "dev"
		}
	}
	return strings.ToLower(mode)
}

func IsTestProfile() bool {
	return GetProfile() == "test"
}
