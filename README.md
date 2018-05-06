<p>
	<a href="https://travis-ci.org/mageddo/bookmark-notes"><img src="https://travis-ci.org/mageddo/bookmark-notes.svg?branch=master" alt="Build Status"></img></a>
</p>

### Features
![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/001-bookmarks-list-thumb.jpg)
* Bookmarks list like thumbs
* [Markdown editor
* [Code highlight
* [Tag you bookmark
* [Tag list filter
* [Search by everything
	* Search field to find by bookmark name, content and tags names]
	* Tag list to filter your bookmarks
* Logviewer
* Mobile friendly
* Make your bookmarks public

[See more screenshots](https://mageddo.github.io/bookmark-notes/docs/)


### Running With Docker

1. Run image (It can take some seconds to server start to listen)

```bash
$ git clone https://github.com/mageddo/bookmark-notes.git && cd bookmark-notes
$ docker-compose up prod-dns-bk prod-app-bk prod-nginx-bk
...
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=GET /api/v1.0/settings/map
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=GET /api/v1.0/settings
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=PATCH /api/v1.0/settings
2018/05/06 06:31:10 DEBUG f=app.go:13 pkg=main m=main status=starting...
2018-05-06 06:31:10:977 - info: m=coresetup, profile=prod
2018-05-06 06:31:11:429 - info: m=buildDatabase, status=get-versions, versions=0
2018-05-06 06:31:11:430 - info: database updated
```

2. Access browser [http://bookmarks.intranet](http://bookmarks.intranet)
3. That's it :)
