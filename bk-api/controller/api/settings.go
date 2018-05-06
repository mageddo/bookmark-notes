package api

import (
	"context"
	"net/http"
	"bk-api/service"
	log "github.com/mageddo/go-logging"
	"encoding/json"
	"bk-api/errors"
	. "bk-api/controller"
	"strings"
)

func init() {

	Get("/api/v1.0/settings/map", func(ctx context.Context, w http.ResponseWriter, r *http.Request) {

		w.Header().Set("Content-Type", "application/json")

		log.Infof("status=begin")

		writer := json.NewEncoder(w)
		sc := service.NewSettingsService()
		rows, err := sc.FindAllAsMap()
		if err != nil {
			log.Warningf("status=failed-load-settings, err=%v", err)
			if serr, ok := err.(*errors.ServiceError); ok {
				BadRequest(w, serr.Error())
			} else {
				BadRequest(w, "Could not read settings")
			}
			return
		}
		writer.Encode(rows)
	})

	Get("/api/v1.0/settings", func(ctx context.Context, w http.ResponseWriter, r *http.Request) {

		w.Header().Set("Content-Type", "application/json")

		key := strings.TrimSpace(r.URL.Query().Get("key"))
		log.Infof("status=begin, key=%s", key)

		writer := json.NewEncoder(w)
		sc := service.NewSettingsService()
		if key == "" {
			rows, err := sc.FindAll()
			if err != nil {
				log.Warningf("status=failed-load-settings, err=%v", err)
				if serr, ok := err.(*errors.ServiceError); ok {
					BadRequest(w, serr.Error())
				} else {
					BadRequest(w, "Could not read settings")
				}
				return
			}
			writer.Encode(rows)
			return
		}
		s, err := sc.GetSetting(key)
		if err != nil {
			log.Warningf("status=failed-load-setting, err=%v", err)
			if serr, ok := err.(*errors.ServiceError); ok {
				BadRequest(w, serr.Error())
			} else {
				BadRequest(w, "Could not read settings")
			}
			return
		}

		writer.Encode(s)
		log.Infof("status=success, key=%s", key)

	})
}
