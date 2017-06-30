--
-- File generated with SQLiteStudio v3.0.6 on qua out 21 18:51:13 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (tagId INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE, bookmarkId INTEGER NOT NULL REFERENCES bookmark (id) ON DELETE CASCADE, PRIMARY KEY (tagId, bookmarkId));
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (2, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (4, 3);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (114, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (115, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (116, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (117, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (49, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (117, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (118, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 38);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (132, 38);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 39);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (133, 39);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (4, 40);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (134, 40);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (135, 40);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (34, 40);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 1);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (134, 41);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (136, 41);

-- Table: bookmark
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (500), link VARCHAR (1000), html TEXT);
INSERT INTO bookmark (id, name, link, html) VALUES (1, 'primeiro bookmark', 'bookmarks', '# Header Level 1

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
INSERT INTO bookmark (id, name, link, html) VALUES (28, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (29, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (30, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (31, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (32, 'test $6', 'dasd', 'sadsada');
INSERT INTO bookmark (id, name, link, html) VALUES (33, 'teste $7', 'qdasd', 'sadsad.sad');
INSERT INTO bookmark (id, name, link, html) VALUES (34, 'tete', 'asdsa', 'sadasd
asd
sad
ad
as');
INSERT INTO bookmark (id, name, link, html) VALUES (35, 'teste $8', 'dasdsad', 'sadsad
adsa
sdas
das
');
INSERT INTO bookmark (id, name, link, html) VALUES (36, 'dasd', 'adasd', 'asdsada');
INSERT INTO bookmark (id, name, link, html) VALUES (37, 'deFreitas', 'sad', 'sadsad');
INSERT INTO bookmark (id, name, link, html) VALUES (38, 'asdsad', 'dasd', 'sadasd');
INSERT INTO bookmark (id, name, link, html) VALUES (39, 'sadsad 10', 'asdasd 10', 'asdasd
asda
sd
asd
as
dsad10');
INSERT INTO bookmark (id, name, link, html) VALUES (40, 'teste 11 editado', 'dsadsa editado', 'asd
ada
sd
as 111 editado');
INSERT INTO bookmark (id, name, link, html) VALUES (41, 'sadsad', 'adsad', 'sadsad
### dsad
## dsad
');
INSERT INTO bookmark (id, name, link, html) VALUES (42, '', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (43, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (44, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (45, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (46, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (47, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (48, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (49, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (50, 'bookmark de mock', NULL, NULL);
INSERT INTO bookmark (id, name, link, html) VALUES (51, 'bookmark de mock', NULL, NULL);

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
INSERT INTO tag (id, name, slug) VALUES (104, 'jgahrsdhvjthuxr', 'jgahrsdhvjthuxr');
INSERT INTO tag (id, name, slug) VALUES (105, 'Tag1', 'tag1');
INSERT INTO tag (id, name, slug) VALUES (106, 'Tag2', 'tag2');
INSERT INTO tag (id, name, slug) VALUES (107, 'Tag3', 'tag3');
INSERT INTO tag (id, name, slug) VALUES (108, 'qn2ne0s7gfirudi', 'qn2ne0s7gfirudi');
INSERT INTO tag (id, name, slug) VALUES (109, 'l4n596f0zarlik9', 'l4n596f0zarlik9');
INSERT INTO tag (id, name, slug) VALUES (110, 'kembindi61ug14i', 'kembindi61ug14i');
INSERT INTO tag (id, name, slug) VALUES (111, 'g84v0i3iur0y66r', 'g84v0i3iur0y66r');
INSERT INTO tag (id, name, slug) VALUES (112, 'outra tag', 'outra-tag');
INSERT INTO tag (id, name, slug) VALUES (113, 'xnifp7bo9f3whfr', 'xnifp7bo9f3whfr');
INSERT INTO tag (id, name, slug) VALUES (114, '1', '1');
INSERT INTO tag (id, name, slug) VALUES (115, '49', '49');
INSERT INTO tag (id, name, slug) VALUES (116, 'coca cola', 'coca-cola');
INSERT INTO tag (id, name, slug) VALUES (117, 'dev', 'dev');
INSERT INTO tag (id, name, slug) VALUES (118, 'novata', 'novata');
INSERT INTO tag (id, name, slug) VALUES (119, 'r3ozg8s0sc680k9', 'r3ozg8s0sc680k9');
INSERT INTO tag (id, name, slug) VALUES (120, 'd47rr3qx2tbuik9', 'd47rr3qx2tbuik9');
INSERT INTO tag (id, name, slug) VALUES (121, '.tkbduylqujif6r', 'tkbduylqujif6r');
INSERT INTO tag (id, name, slug) VALUES (122, 'hg2fum6sat3mcxr', 'hg2fum6sat3mcxr');
INSERT INTO tag (id, name, slug) VALUES (123, 'm1n9t7avx2h85mi', 'm1n9t7avx2h85mi');
INSERT INTO tag (id, name, slug) VALUES (124, '.uw1hux86fos9k9', 'uw1hux86fos9k9');
INSERT INTO tag (id, name, slug) VALUES (125, '5741uhevpxh6w29', '5741uhevpxh6w29');
INSERT INTO tag (id, name, slug) VALUES (126, 'fwgutk9a20ara4i', 'fwgutk9a20ara4i');
INSERT INTO tag (id, name, slug) VALUES (127, 'natal', 'natal');
INSERT INTO tag (id, name, slug) VALUES (128, 'teste8', 'teste8');
INSERT INTO tag (id, name, slug) VALUES (129, 'novo9', 'novo9');
INSERT INTO tag (id, name, slug) VALUES (130, 'olasd', 'olasd');
INSERT INTO tag (id, name, slug) VALUES (131, 'outranova', 'outranova');
INSERT INTO tag (id, name, slug) VALUES (132, '208b', '208b');
INSERT INTO tag (id, name, slug) VALUES (133, 'teste9', 'teste9');
INSERT INTO tag (id, name, slug) VALUES (134, 'name', 'name');
INSERT INTO tag (id, name, slug) VALUES (135, 'teste11', 'teste11');
INSERT INTO tag (id, name, slug) VALUES (136, 'novo cara', 'novo-cara');

-- View: bookmarkList
DROP VIEW IF EXISTS bookmarkList;
CREATE VIEW bookmarkList AS WITH FILTER AS (SELECT * FROM bookmark)
SELECT * FROM FILTER LIMIT 0,100;

-- View: tagView
DROP VIEW IF EXISTS tagView;
CREATE VIEW tagView AS SELECT t.*, tb.bookmarkId FROM tag t
LEFT JOIN tagBookmark tb
ON t.id = tb.tagId;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
