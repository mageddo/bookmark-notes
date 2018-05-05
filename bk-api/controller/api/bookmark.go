package api

import (
	"context"
	"net/http"
	"bk-api/service"
	"github.com/mageddo/go-logging"
	"strconv"
	"encoding/json"
	"bk-api/entity"
	"fmt"
	"bk-api/errors"
	. "bk-api/controller"
)

const (
	BOOKMARK_V1 = "/api/v1.0/bookmark"
)

func init(){

	Get(BOOKMARK_V1, func(ctx context.Context, w http.ResponseWriter, r *http.Request){

		w.Header().Set("Content-Type", "application/json")

		from, err := strconv.Atoi(r.URL.Query().Get("from"))
		quantity, errQtd := strconv.Atoi(r.URL.Query().Get("quantity"))
		tagSlug := r.URL.Query().Get("tag")
		searchQuery := r.URL.Query().Get("query")

		logging.Infof("status=begin, from=%d, quantity=%d, tag=%s, search=%s", from, quantity, tagSlug, searchQuery)

		if err != nil || errQtd != nil {
			BadRequest(w, "Please pass a valid offset and quantity")
			logging.Infof("status=bad-parameters, err=%v, errQtd=%v", err, errQtd)
			return
		}

		writer := json.NewEncoder(w)
		sc := service.NewBookmarkService()
		bookmarks, length, err := sc.GetBookmarks(from, quantity, tagSlug, searchQuery)
		if err != nil {

			logging.Warningf("status=failed-load-bookmark, err=%v, errQtd=%v", err, errQtd)
			if serr, ok := err.(*errors.ServiceError); ok {
				BadRequest(w, serr.Error())
			}else{
				BadRequest(w, "Could not read bookmarks")
			}

			return
		}

		fmt.Fprint(w, "[")
		type BookmarkVO entity.BookmarkEntity
		for i, b := range bookmarks {
			if i != 0 {
				fmt.Fprint(w, ",")
			}
			writer.Encode(&struct{
				*BookmarkVO
				Length int `json:"length"`
			}{
				(*BookmarkVO)(&b),
				length,
			})
		}
		fmt.Fprint(w, "]")
	})

}
