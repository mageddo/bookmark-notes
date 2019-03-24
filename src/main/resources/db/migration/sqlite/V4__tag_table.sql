CREATE TABLE IF NOT EXISTS TAG (
	IDT_TAG INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	NAM_TAG VARCHAR (100) NOT NULL,
	COD_SLUG VARCHAR (100) NOT NULL UNIQUE,
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME
);
CREATE TABLE TAG_BOOKMARK (
	IDT_TAG INTEGER NOT NULL REFERENCES TAG(IDT_TAG),
	IDT_BOOKMARK INTEGER NOT NULL REFERENCES BOOKMARK(IDT_BOOKMARK),
	DAT_CREATION DATETIME DEFAULT CURRENT_TIMESTAMP,
	DAT_UPDATE DATETIME,
	PRIMARY KEY (IDT_TAG, IDT_BOOKMARK)
);