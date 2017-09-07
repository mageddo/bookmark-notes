package utils

import (
	"os"
	"encoding/json"
	"bk-api/log"
	"path/filepath"
	"strings"
	"bk-api/env"
)

type Configuration struct {
	DatabaseURL string `json:"databaseUrl"`
	Connections int `json:"connections"`
}

var configuration = Configuration{}

func init(){

	confFile, _ := os.Open(GetPath("conf/application-" + env.GetProfile() + ".json"))
	decoder := json.NewDecoder(confFile)
	err := decoder.Decode(&configuration)
	if err != nil {
		log.LOGGER.Fatalf("m=conf, status=loadconf, err=%v", err)
		os.Exit(-1)
	}

}

func GetConfig() *Configuration {
	return &configuration
}

func GetCurrentPath() string {

	currDIr := os.Getenv("MG_WORK_DIR")
	if len(currDIr) != 0 {
		return currDIr
	}
	currentPath, _ := filepath.Abs(filepath.Dir(os.Args[0]))
	log.LOGGER.Info("currentPath=%s", currentPath)
	return currentPath

}

func GetPath(path string) string {
	if !strings.HasPrefix(path, "/") {
		path = "/" + path
	}
	currentPath := GetCurrentPath();
	if strings.HasSuffix(currentPath, "/") {
		currentPath = currentPath[0:len(currentPath)-1];
	}
	newPath := currentPath + path
	log.LOGGER.Infof("newPath=%s", newPath)
	return newPath
}
