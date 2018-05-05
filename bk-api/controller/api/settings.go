package api

import (
	"context"
	"net/http"
	"bk-api/service"
	"github.com/mageddo/go-logging"
	"encoding/json"
	"bk-api/errors"
	. "bk-api/controller"
	"strings"
)

func init() {

	Get("/api/v1.0/settings", func(ctx context.Context, w http.ResponseWriter, r *http.Request) {

		w.Header().Set("Content-Type", "application/json")

		logger := logging.NewLog(ctx)
		key := strings.TrimSpace(r.URL.Query().Get("key"))
		logger.Infof("status=begin, key=%s", key)

		if key == "" {
			BadRequest(w, "Please pass a valid key")
			return
		}

		sc := service.NewSettingsService()
		s, err := sc.GetSetting(key)
		if err != nil {
			logger.Warningf("status=failed-load-bookmark, err=%v, errQtd=%v", err)
			if serr, ok := err.(*errors.ServiceError); ok {
				BadRequest(w, serr.Error())
			} else {
				BadRequest(w, "Could not read bookmarks")
			}
			return
		}

		writer := json.NewEncoder(w)
		writer.Encode(s)
	})
}
