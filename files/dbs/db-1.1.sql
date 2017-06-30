--
-- File generated with SQLiteStudio v3.0.6 on seg out 5 18:00:13 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, tagId INTEGER UNIQUE REFERENCES tag (id) ON DELETE CASCADE NOT NULL, bookmarkId INTEGER REFERENCES bookmark (id) ON DELETE CASCADE UNIQUE NOT NULL);

-- Table: bookmark
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (500), link VARCHAR (1000), html TEXT);
INSERT INTO bookmark (id, name, link, html) VALUES (1, '', '', '# Header Level 1

**Pellentesque habitant morbi tristique** senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. _Aenean ultricies mi vitae est_. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, `commodo vitae`, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum  rutrum orci, sagittis tempus lacus enim ac dui. [Donec non enim](#) in turpis pulvinar facilisis. Ut felis.

## Header Level 2

  1. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  2. Aliquam tincidunt mauris eu risus.


> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur  massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est.

### Header Level 3

  * Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  * Aliquam tincidunt mauris eu risus.

```css
#header h1 a {
  display: block;
  width: 300px;
  height: 80px;
}
```');
INSERT INTO bookmark (id, name, link, html) VALUES (2, '', '', '# Header Level 1

**Pellentesque habitant morbi tristique** senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. _Aenean ultricies mi vitae est_. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, `commodo vitae`, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum  rutrum orci, sagittis tempus lacus enim ac dui. [Donec non enim](#) in turpis pulvinar facilisis. Ut felis.

## Header Level 2

  1. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  2. Aliquam tincidunt mauris eu risus.


> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur  massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est.

### Header Level 3

  * Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  * Aliquam tincidunt mauris eu risus.

```css
#header h1 a {
  display: block;
  width: 300px;
  height: 80px;
}
```');
INSERT INTO bookmark (id, name, link, html) VALUES (3, NULL, NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (4, 'Tetste 1', '', '# Header Level 1

**Pellentesque habitant morbi tristique** senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. _Aenean ultricies mi vitae est_. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, `commodo vitae`, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum  rutrum orci, sagittis tempus lacus enim ac dui. [Donec non enim](#) in turpis pulvinar facilisis. Ut felis.

## Header Level 2

  1. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  2. Aliquam tincidunt mauris eu risus.


> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur  massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est.

### Header Level 3

  * Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
  * Aliquam tincidunt mauris eu risus.

```css
#header h1 a {
  display: block;
  width: 300px;
  height: 80px;
}
```');
INSERT INTO bookmark (id, name, link, html) VALUES (5, 'test 1', '#1', 'teste');
INSERT INTO bookmark (id, name, link, html) VALUES (6, 'test 2', '#2', '# teste2');
INSERT INTO bookmark (id, name, link, html) VALUES (7, 'test3', '#test3', 'test3');
INSERT INTO bookmark (id, name, link, html) VALUES (8, 'test 3', 'test3', 'test');
INSERT INTO bookmark (id, name, link, html) VALUES (9, 'test3', 'test3', 'test');

-- Table: tag
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (100) NOT NULL, slug VARCHAR (100) NOT NULL UNIQUE);
INSERT INTO tag (id, name, slug) VALUES (1, 'Elvis', 'elvis');
INSERT INTO tag (id, name, slug) VALUES (2, 'name', 'slug');
INSERT INTO tag (id, name, slug) VALUES (3, 'Elvis Teste', 'elvis-teste');
INSERT INTO tag (id, name, slug) VALUES (4, 'sdsadsadasd', 'sdsadsadasd');
INSERT INTO tag (id, name, slug) VALUES (5, 'adasdsadsad', 'adasdsadsad');
INSERT INTO tag (id, name, slug) VALUES (6, 'sadsadsad', 'sadsadsad');
INSERT INTO tag (id, name, slug) VALUES (7, 'asadsad', 'asadsad');
INSERT INTO tag (id, name, slug) VALUES (8, 'asdasdasd', 'asdasdasd');
INSERT INTO tag (id, name, slug) VALUES (9, 'sdadsad', 'sdadsad');
INSERT INTO tag (id, name, slug) VALUES (10, 'jao', 'jao');
INSERT INTO tag (id, name, slug) VALUES (11, 'joao', 'joao');

-- View: bookmarkList
DROP VIEW IF EXISTS bookmarkList;
CREATE VIEW bookmarkList AS WITH FILTER AS (SELECT * FROM bookmark)
SELECT *, (SELECT COUNT(*) FROM FILTER) as length
FROM FILTER LIMIT 0,100;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
