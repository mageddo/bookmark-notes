package log

import (
	"bk-api/env"
)

func init(){
	mode := env.GetProfile()
	switch mode {
	case "prod":
		//%{time:06-01-02 15:04:05} %{level:.3s} %{message}
		break
	default:
		//`%{color}%{time:06-01-02 15:04:05.000} %{level:.3s} %{color:reset}%{message}`
		break
	}
}
