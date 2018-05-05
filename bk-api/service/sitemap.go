package service

import (
	"bk-api/dao"
	"io"
	"fmt"
	"github.com/mageddo/go-optional"
	"time"
	"strings"
)

type SiteMapService struct {
	bookmarkDAO dao.BookmarkDAO
}

func (s *SiteMapService) LoadSiteMap(w io.Writer, url string) (error) {
	bookmarks, err := s.bookmarkDAO.LoadSiteMap()
	if err != nil {
		return err
	}
	fmt.Fprint(w, `<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">`)

	for _, b := range bookmarks {

		fmt.Fprintf(w, `
	<url>
		<loc>%s</loc>
		%s
		<changefreq>weekly</changefreq>
		<priority>1</priority>
	</url>`, fmt.Sprintf("%s/bookmark/%d/%s", url, b.Id, strings.Replace(strings.ToLower(*b.Name), " ", "-", -1)),
			optional.OfNullable(b.Update).
			Map(func(date interface{}) interface{}{
				return fmt.Sprintf(`<lastmod>%s</lastmod>`, date.(*time.Time).Format("2006-01-02"))
			}).OrElse(""))

	}
	fmt.Fprintln(w, "</urlset>")
	return err
}

func NewSiteMapService() SiteMapService {
	return SiteMapService{dao.NewBookmarkDAO() }
}

