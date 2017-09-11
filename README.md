<p>
	<a href="https://travis-ci.org/mageddo/bookmark-notes"><img src="https://travis-ci.org/mageddo/bookmark-notes.svg?branch=master" alt="Build Status"></img></a>
</p>

# Features
![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/001-bookmarks-list-thumb.jpg)

* [Bookmarks list like thumbs](#001)
* [Markdown editor](#002)
* [Code highlight](#003)
* [Tag you bookmark](#005)
* [Tag list filter](#006)
* [Search by everything](#007)
	* Search field to find by bookmark name, content and tags names]
	* Tag list to filter your bookmarks
* [Logviewer](#008)
* Mobile friendly
* [Make your bookmarks public](#009)


# Running With Docker

1. Run image

		$ docker run --rm -p 3000:3000 --name bookmarks defreitas/bookmark-notes
		...
		2017-07-06 23:14:13:942 - debug: loading: HomeController.js
		2017-07-06 23:14:13:942 - debug: loading: TagController.js
		2017-07-06 23:14:13:943 - debug: loading: TesteController.js
		2017-07-06 23:14:14:629 - info: m=getSystemVersionCb, dbversion=1.7, currentVersion=1.7

2. Access browser [http://localhost:3000](http://localhost:3000)
3. That's it :)


# Building from source (You probably don't need to do that)

	$ docker-compose up --force-recreate prod-api-build-bk && docker-compose build prod-build-bk

Then

	$ docker run --rm -p 3000:3000 --name bookmarks defreitas/bookmark-notes:<docker-compose-tag>

if you have problems to stop the container try

	$ docker kill bookmarks


# Logs

See page [log page](http://127.0.0.1:3000/logviewer/#)

# Screenshots
### <div id="001">Bookmarks list like thumbs</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/001-bookmarks-list-thumb.jpg)

### <div id="002">Markdown editor</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/002-markdown-editor.jpg)

### <div id="003">Code highlight</div>

* ![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/003-code-highlight.jpg)
* ![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/004-code-highlight.jpg)

### <div id="005">Tag you bookmark</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/005-bookmark-tag.jpg)

### <div id="006">Tag list filter</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/006-tag-list-filter.jpg)

### <div id="007">Search by everything</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/007-search.jpg)

### <div id="008">Logviewer</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/008-logviewer.jpg)

### <div id="009">Make your bookmarks public</div>

![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/010-turn-it-public.png)
![](https://raw.githubusercontent.com/mageddo/bookmark-notes/master/files/screenshots/009-public-bookmark-view.png)

