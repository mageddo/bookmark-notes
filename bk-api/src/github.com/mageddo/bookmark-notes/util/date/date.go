package date

import "time"

func GetFormattedDate(date *time.Time) *string {
	if date == nil {
		return nil
	} else {
		str := date.Format("2006-01-02 15:04:05")
		return &str
	}
}

