## Deprecated
Use https://github.com/hackmdio/codimd or  https://hackmd.io/ instead

### Features
![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/001-bookmarks-list-thumb.jpg)
* Bookmarks list like thumbs
* Markdown editor
* Code highlight
* Tag you bookmark
* Tag list filter
* Search by everything
	* Search field to find by bookmark name, content and tags names
	* Tag list to filter your bookmarks
* Logviewer
* Mobile friendly
* Make your bookmarks public

[See more screenshots](https://mageddo.github.io/bookmark-notes/docs/)


### Running without Docker

1. Run image (It can take some seconds to server start to listen)

```bash
$ docker-compose up postgres
$ ./gradlew run
Server Running: http://localhost:8080
```

2. Access browser [http://localhost:8080](http://localhost:8080)
3. That's it :)

### Running with Docker

```
$ docker-compose -f docker-compose-prod.yml rm -f
$ docker-compose -f docker-compose-prod.yml up postgres
$ docker-compose -f docker-compose-prod.yml up bookmark-notes
```

Access browser [http://localhost:8080](http://localhost:8080)
