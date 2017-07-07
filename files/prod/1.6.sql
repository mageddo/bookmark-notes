--
-- File generated with SQLiteStudio v3.0.6 on dom nov 22 16:20:27 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: bookmark
-- All bookmarks are saved here
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (
	id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL,
	name VARCHAR (500), -- the title of the bookmark
	link VARCHAR (1000), -- the link at the title of the bookmark
	html TEXT, -- the raw bookmark content
--
	-- by default the bookmark is not deleted when you click at "delete", it is only hidden for recover reasons
	-- in the future the idea is to create a routine that deletes permantly bookmarks with deleted=1 30 days old
	deleted BOOLEAN DEFAULT (0), 
	archived BOOLEAN DEFAULT (0) -- flag that specify if it was archived
);

-- Table: tagBookmark
-- The relation between a bookmark and a tag, that way a bookmark can have many tags 
--- and the tags are not dependent of the bookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (
	tagId INTEGER NOT NULL REFERENCES tag (id),
	bookmarkId INTEGER NOT NULL REFERENCES bookmark (id),
	PRIMARY KEY (tagId, bookmarkId)
);

-- Table: tag
-- It's a label to mark the bookmark, a bookmark can have many of this
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
	id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	name VARCHAR (100) NOT NULL,
	slug VARCHAR (100) NOT NULL UNIQUE
);

-- View: bookmarkList
-- To simplify the bookmark select
DROP VIEW IF EXISTS bookmarkList;
CREATE VIEW bookmarkList AS 
	WITH FILTER AS (SELECT * FROM bookmark)
	SELECT * FROM FILTER LIMIT 0,100;

-- View: tagView
DROP VIEW IF EXISTS tagView;
CREATE VIEW tagView AS
	SELECT t.*, tb.bookmarkId FROM tag t
	LEFT JOIN tagBookmark tb
	ON t.id = tb.tagId;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
