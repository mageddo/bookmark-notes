--
-- File generated with SQLiteStudio v3.0.6 on qua out 14 17:30:06 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

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
INSERT INTO tag (id, name, slug) VALUES (12, 'dasdsada', 'dasdsada');
INSERT INTO tag (id, name, slug) VALUES (13, 'sadsadasdas', 'sadsadasdas');
INSERT INTO tag (id, name, slug) VALUES (14, 'sadsadsadasdsad', 'sadsadsadasdsad');
INSERT INTO tag (id, name, slug) VALUES (15, 'akjlkjkjsa', 'akjlkjkjsa');
INSERT INTO tag (id, name, slug) VALUES (16, 'sadsadkjaskhdas', 'sadsadkjaskhdas');
INSERT INTO tag (id, name, slug) VALUES (17, 'olsadsa', 'olsadsa');
INSERT INTO tag (id, name, slug) VALUES (18, 'olssa', 'olssa');
INSERT INTO tag (id, name, slug) VALUES (19, 'vms', 'vms');
INSERT INTO tag (id, name, slug) VALUES (20, 'sl', 'sl');
INSERT INTO tag (id, name, slug) VALUES (21, 'dasdsad', 'dasdsad');
INSERT INTO tag (id, name, slug) VALUES (22, 'dasda', 'dasda');
INSERT INTO tag (id, name, slug) VALUES (23, 'elivs', 'elivs');
INSERT INTO tag (id, name, slug) VALUES (24, 'jaoo', 'jaoo');
INSERT INTO tag (id, name, slug) VALUES (25, 'marcelo', 'marcelo');
INSERT INTO tag (id, name, slug) VALUES (26, 'novo', 'novo');
INSERT INTO tag (id, name, slug) VALUES (27, 'testa', 'testa');
INSERT INTO tag (id, name, slug) VALUES (28, 'okas', 'okas');
INSERT INTO tag (id, name, slug) VALUES (29, 'nao existe', 'nao-existe');
INSERT INTO tag (id, name, slug) VALUES (30, 'mais um no', 'mais-umno');
INSERT INTO tag (id, name, slug) VALUES (31, 'mais um novo', 'mais-umnovo');
INSERT INTO tag (id, name, slug) VALUES (32, 'outro novo', 'outro-novo');
INSERT INTO tag (id, name, slug) VALUES (34, 'Elvis segundo', 'elvis-segundo');
INSERT INTO tag (id, name, slug) VALUES (36, 'Elvis segundo', 'elvis-terceiro');
INSERT INTO tag (id, name, slug) VALUES (37, 'Elvis quarto', 'elvis-quarto');
INSERT INTO tag (id, name, slug) VALUES (41, 'Elvis quarto', 'elvis-quinto');
INSERT INTO tag (id, name, slug) VALUES (42, 'Tag de teste $ !', 'tag-deteste');
INSERT INTO tag (id, name, slug) VALUES (43, 'microsoft', 'microsoft');
INSERT INTO tag (id, name, slug) VALUES (46, 'youtube', 'youtube');
INSERT INTO tag (id, name, slug) VALUES (49, 'java', 'java');
INSERT INTO tag (id, name, slug) VALUES (53, 'teste', 'teste');
INSERT INTO tag (id, name, slug) VALUES (67, 'Tag de teste $ !', 'tagdeteste');
INSERT INTO tag (id, name, slug) VALUES (68, 'Tag de teste $ !', 'tagde teste  ');
INSERT INTO tag (id, name, slug) VALUES (69, 'Tag de teste $ !', 'tag-de-teste');
INSERT INTO tag (id, name, slug) VALUES (87, '$ !', '');
INSERT INTO tag (id, name, slug) VALUES (94, 'testando', 'testando');
INSERT INTO tag (id, name, slug) VALUES (98, 'meu teste 5', 'meu-teste');

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
INSERT INTO bookmark (id, name, link, html) VALUES (10, 'testando tags 1', '', '');
INSERT INTO bookmark (id, name, link, html) VALUES (11, '', '', '');
INSERT INTO bookmark (id, name, link, html) VALUES (12, 'b com tags #2', 'http://google.com', '# cadastrando ');
INSERT INTO bookmark (id, name, link, html) VALUES (13, 'b com tags #2', 'http://google.com', '# cadastrando ');
INSERT INTO bookmark (id, name, link, html) VALUES (14, 'b com tags #2', 'http://google.com', '# cadastrando ');
INSERT INTO bookmark (id, name, link, html) VALUES (15, 'b com tags #2', 'http://google.com', '# cadastrando ');
INSERT INTO bookmark (id, name, link, html) VALUES (16, 'bookmark tag #3', 'http://stackoverflow.com', '# testando mais uma vez');
INSERT INTO bookmark (id, name, link, html) VALUES (17, 'bancos ', 'nwelink', 'minha api cara');
INSERT INTO bookmark (id, name, link, html) VALUES (18, 'john', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (19, 'john2', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (20, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (21, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (22, 'teste $55', 'google.com', 'sadsa');
INSERT INTO bookmark (id, name, link, html) VALUES (23, 'teste $5', 'novo link', 'dasd;asjd;lsa');
INSERT INTO bookmark (id, name, link, html) VALUES (24, 'teste $5', 'novo link', 'dasd;asjd;lsa');
INSERT INTO bookmark (id, name, link, html) VALUES (25, 'teste $5', 'novo link', 'dasd;asjd;lsa');
INSERT INTO bookmark (id, name, link, html) VALUES (26, 'teste $5', 'novo link', 'dasd;asjd;lsa');
INSERT INTO bookmark (id, name, link, html) VALUES (27, 'teste $6', 'dsad', 'dasdsd
');

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (tagId INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE, bookmarkId INTEGER NOT NULL REFERENCES bookmark (id) ON DELETE CASCADE, PRIMARY KEY (tagId, bookmarkId));
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 2);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (2, 2);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 2);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (2, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (49, 27);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (98, 27);

-- View: bookmarkList
DROP VIEW IF EXISTS bookmarkList;
CREATE VIEW bookmarkList AS WITH FILTER AS (SELECT * FROM bookmark)
SELECT *, (SELECT COUNT(*) FROM FILTER) as length
FROM FILTER LIMIT 0,100;

-- View: tagView
DROP VIEW IF EXISTS tagView;
CREATE VIEW tagView AS SELECT t.*, tb.bookmarkId FROM tag t
LEFT JOIN tagBookmark tb
ON t.id = tb.tagId;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
