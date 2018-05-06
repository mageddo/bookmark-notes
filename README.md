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

1. Run image

```bash
$ git clone https://github.com/mageddo/bookmark-notes.git && bookmark-notes && docker-compose up prod-dns-bk prod-app-bk prod-nginx-bk
...
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=GET /api/v1.0/settings/map
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=GET /api/v1.0/settings
2018/05/06 06:31:10 INFO f=index.go:64 pkg=bk-api/controller m=handle status=registering, path=PATCH /api/v1.0/settings
2018/05/06 06:31:10 DEBUG f=app.go:13 pkg=main m=main status=starting...
2018-05-06 06:31:10:977 - info: m=coresetup, profile=prod
2018-05-06 06:31:11:429 - info: m=buildDatabase, status=get-versions, versions=0
2018-05-06 06:31:11:430 - info: database updated
```

2. Access browser [http://localhost:3000](http://localhost:3000)
3. That's it :)


### Building from source (You probably don't need to do that)

```bash
$ docker-compose up --abort-on-container-exit --force-recreate prod-api-build-bk && \
docker-compose build prod-build-bk
```

Then

```
$ docker run --rm -p 3000:3000 --name bookmarks defreitas/bookmark-notes:<docker-compose-tag>
```

if you have problems to stop the container try

```
$ docker kill bookmarks
````

### Logs

See page [log page](http://127.0.0.1:3000/logviewer/#)

