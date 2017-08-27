package controller

import (
	"context"
	"net/http"
	"github.com/mageddo/bookmark-notes/service"
	"github.com/mageddo/go-logging"
	"strconv"
	"encoding/json"
	"github.com/mageddo/bookmark-notes/entity"
	"fmt"
	"github.com/mageddo/bookmark-notes/errors"
)

func init(){

	Get("/api/v1.0/bookmark", func(ctx context.Context, w http.ResponseWriter, r *http.Request){


		logger := logging.NewLog(ctx)
		from, err := strconv.Atoi(r.URL.Query().Get("from"))
		quantity, errQtd := strconv.Atoi(r.URL.Query().Get("quantity"))
		tagSlug := r.URL.Query().Get("tag")
		searchQuery := r.URL.Query().Get("query")

		logger.Infof("status=begin, from=%d, quantity=%d, tag=%s, search=%s", from, quantity, tagSlug, searchQuery)

		if err != nil || errQtd != nil {
			BadRequest(w, "Please pass a valid offset and quantity")
			logger.Infof("status=bad-parameters, err=%v, errQtd=%v", err, errQtd)
			return
		}

		writer := json.NewEncoder(w)
		sc := service.NewBookmarkService(ctx)
		bookmarks, length, err := sc.GetBookmarks(from, quantity, tagSlug, searchQuery)
		if err != nil {

			logger.Warningf("status=failed-load-bookmark, err=%v, errQtd=%v", err, errQtd)
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
