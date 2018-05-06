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

```
$ docker run --rm -p 3000:3000 --name bookmarks defreitas/bookmark-notes
...
2017-07-06 23:14:13:942 - debug: loading: HomeController.js
2017-07-06 23:14:13:942 - debug: loading: TagController.js
2017-07-06 23:14:13:943 - debug: loading: TesteController.js
2017-07-06 23:14:14:629 - info: m=getSystemVersionCb, dbversion=1.7, currentVersion=1.7
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

