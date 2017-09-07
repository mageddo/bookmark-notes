package utils

import (
	"os"
	"encoding/json"
	"fmt"
)

type Configuration struct {
	DatabaseURL string `json:"databaseUrl"`
}

var configuration = Configuration{}

func init(){

	file, _ := os.Open("conf/application-" + GetProfile() + ".json")
	decoder := json.NewDecoder(file)
	err := decoder.Decode(&configuration)
	if err != nil {
		fmt.Println("error:", err)
	}

}

func GetConfig() *Configuration {
	return &configuration
}
