package utils

import "time"


var now *time.Time = nil

func Now() time.Time {
	if now != nil {
		return *now
	}
	return time.Now().UTC()
}

func SetNow(pnow time.Time){
	now = &pnow;
}
