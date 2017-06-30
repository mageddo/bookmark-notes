PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- System database
-- This table is used to save system settings and general information about the application
CREATE TABLE systemProperty (
	name VARCHAR(25) UNIQUE NOT NULL,
	value VARCHAR(100)
);

INSERT INTO systemProperty VALUES ('DB_VERSION', '1.7');

-- creating column to set bookmark visibility
-- 0 -> private
-- 1 -> public
ALTER TABLE bookmark ADD COLUMN visibility TINYINT DEFAULT 0;

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
