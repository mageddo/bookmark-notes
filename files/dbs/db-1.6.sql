--
-- File generated with SQLiteStudio v3.0.6 on dom nov 22 16:20:27 2015
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
INSERT INTO tag (id, name, slug) VALUES (137, 'nova', 'nova');
INSERT INTO tag (id, name, slug) VALUES (138, 'prof-ti', 'prof-ti');
INSERT INTO tag (id, name, slug) VALUES (139, 'mudado', 'mudado');

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (tagId INTEGER NOT NULL REFERENCES tag (id), bookmarkId INTEGER NOT NULL REFERENCES bookmark (id), PRIMARY KEY (tagId, bookmarkId));
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (114, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (115, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (116, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (117, 32);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (49, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (117, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (118, 33);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 39);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (133, 39);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (134, 41);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (136, 41);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (1, 52);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (138, 52);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (53, 53);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (4, 54);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 55);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 56);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (4, 57);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 58);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (5, 59);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (8, 62);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (134, 62);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (49, 63);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 65);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (134, 66);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 69);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (4, 70);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 5);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (5, 5);
INSERT INTO tagBookmark (tagId, bookmarkId) VALUES (3, 1);

-- Table: bookmark
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (500), link VARCHAR (1000), html TEXT, deleted BOOLEAN DEFAULT (0), archived BOOLEAN DEFAULT (0));
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (1, 'primeiro bookmark editado 3', 'http://globo.com ', ' <b>negritando novo</b>
# Header Level 1

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
```', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (5, 'test 1', '#1', 'teste

* [google](http://google.com)', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (6, 'test 2', '#2', '# teste2', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (7, 'test3', '#test3', 'test3', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (8, 'test 3', 'test3', 'test', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (9, 'test3', 'test3', 'test', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (13, 'b com tags #2', 'http://google.com', '# cadastrando ', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (14, 'b com tags #2', 'http://google.com', '# cadastrando ', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (15, 'b com tags #2', 'http://google.com', '# cadastrando ', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (16, 'bookmark tag #3', 'http://stackoverflow.com', '# testando mais uma vez', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (17, 'bancos ', 'nwelink', 'minha api cara', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (18, 'john', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (19, 'john2', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (20, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (21, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (22, 'teste $55', 'google.com', 'sadsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (23, 'teste $5', 'novo link', 'dasd;asjd;lsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (24, 'teste $5', 'novo link', 'dasd;asjd;lsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (25, 'teste $5', 'novo link', 'dasd;asjd;lsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (26, 'teste $5', 'novo link', 'dasd;asjd;lsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (27, 'teste $6', 'dsad', 'dasdsd
', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (28, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (29, 'bookmark de mock', '', '', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (30, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (31, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (32, 'test $6', 'dasd', 'sadsada', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (33, 'teste $7', 'qdasd', 'sadsad.sad', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (34, 'tete', 'asdsa', 'sadasd
asd
sad
ad
as', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (35, 'teste $8', 'dasdsad', 'sadsad
adsa
sdas
das
', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (36, 'dasd', 'adasd', 'asdsada', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (37, 'deFreitas', 'sad', 'sadsad', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (39, 'sadsad 10', 'asdasd 10', 'asdasd
asda
sd
asd
as
dsad10', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (41, 'sadsad', 'adsad', 'sadsad
### dsad
## dsad
', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (42, '', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (43, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (44, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (45, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (46, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (47, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (48, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (49, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (50, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (51, 'bookmark de mock', NULL, NULL, 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (52, 'elvis 2', 'goog.le.com', 'adsad
sadsa', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (53, 'codigos de teste', '', 'sadas
dasd
asd
## sadsadasdsad
asdasdadasd

    System.out.println("ola mundo");
    
```javascript
function helloWorld(name){
    alert("hello: " + name);
}
```', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (54, '', '', 'adasdas', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (55, 'adas', '', 'assdas', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (56, 'adas', '', 'assdas', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (57, '', '', 'dasdas', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (58, 'dasd', '', 'assdas', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (59, '', '', '', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (60, 'alterando', '', '', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (61, '', '', '', 0, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (62, 'ele', 'dasd', 'assdas
dasd
assd
ad
sad
elvis', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (63, 'teste', '#', 'asda
dasd
### asdasd
', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (64, '', '', '', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (65, '', '', '', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (66, 'assdas', 'asdsa', 'adas', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (68, 'novo para teste', '', '', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (69, 'nvoo editando', '', 'editando', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (70, 'qualquer', '#', 'sds

dasd', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (73, '', '', 'sfsdf', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (74, 'sdasd', '', 'assdas
dasd
', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (75, '', '', '', 1, 0);
INSERT INTO bookmark (id, name, link, html, deleted, archived) VALUES (76, '', '', 'asd', 1, 0);

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
