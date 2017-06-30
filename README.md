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


# Build and Run With Docker

The settings base directory is `/var/lib/mageddo/bookmarks-node` 

### Create the database file

Create a `/var/lib/mageddo/bookmarks-node/db/1.6.db` sqlite3 database and import `db-1.6.sql` (I suggest [SQLStudio](http://sqlitestudio.pl/?act=download))

### Setup authentication

Create the `/var/lib/mageddo/bookmarks-node/conf/users.htpasswd` authentication file, inside put something like that:

	user:mypassword


### Building 

	$ docker-compose build dev && docker-compose up prod
    

### See the results

If you are using a docker DNS visit `http://bookmarks-node.mageddo:3000`, anyway access `http://localhost:3000` will work.

# Build and Run Without Docker

This aproach is not the default but you can do it of course. The settings base path is project directory.

### Setup database

Create `./db/1.6.db` sqlite3 database and import `db-1.6.sql` (I suggest [SQLStudio](http://sqlitestudio.pl/?act=download))


### Setup authentication 
Create the `./conf/users.htpasswd` authentication file, inside put something like that:

	user:mypassword

### Building 

	$ npm install # install dependencies
	$ npm start # starting 
	$ curl -v 127.0.0.1:3000 # testing the application connection, credentials at conf/users.htpasswd file


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
