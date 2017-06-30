--
-- File generated with SQLiteStudio v3.0.6 on qua out 14 15:55:14 2015
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: tag
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (100) NOT NULL, slug VARCHAR (100) NOT NULL UNIQUE);

-- Table: bookmark
DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (id INTEGER PRIMARY KEY ASC AUTOINCREMENT UNIQUE NOT NULL, name VARCHAR (500), link VARCHAR (1000), html TEXT);

-- Table: tagBookmark
DROP TABLE IF EXISTS tagBookmark;
CREATE TABLE tagBookmark (tagId INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE, bookmarkId INTEGER NOT NULL REFERENCES bookmark (id) ON DELETE CASCADE, PRIMARY KEY (tagId, bookmarkId));

-- View: bookmarkList
DROP VIEW IF EXISTS bookmarkList;
CREATE VIEW bookmarkList AS WITH FILTER AS (SELECT * FROM bookmark)
SELECT *, (SELECT COUNT(*) FROM FILTER) as length
FROM FILTER LIMIT 0,100;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
