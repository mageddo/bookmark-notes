--
-- File generated with SQLiteStudio v3.0.6 on qui out 29 11:11:52 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: bookmark
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (500), link VARCHAR (1000), html TEXT);

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (tagId INTEGER NOT NULL REFERENCES tag (id), bookmarkId INTEGER NOT NULL REFERENCES bookmark (id), PRIMARY KEY (tagId, bookmarkId));

-- Table: tag
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (100) NOT NULL, slug VARCHAR (100) NOT NULL UNIQUE);

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
