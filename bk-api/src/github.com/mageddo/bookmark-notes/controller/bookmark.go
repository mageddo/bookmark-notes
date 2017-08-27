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
)

func init(){

	Get("/api/v1.0/bookmark", func(ctx context.Context, w http.ResponseWriter, r *http.Request){

		logger := logging.NewLog(ctx)
		from, err := strconv.Atoi(r.URL.Query().Get("from"))
		quantity, errQtd := strconv.Atoi(r.URL.Query().Get("quantity"))
		if err != nil || errQtd != nil {
			BadRequest(w, "Please pass a valid offset and quantity")
			logger.Infof("status=bad-parameters, err=%v, errQtd=%v", err, errQtd)
			return
		}

		sc := service.NewBookmarkService(ctx)
		bookmarks, length, err := sc.GetBookmarks(from, quantity)
		if err != nil {
			logger.Errorf("status=failed-load-bookmark, err=%v", err, errQtd)
		}
		writer := json.NewEncoder(w)
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
