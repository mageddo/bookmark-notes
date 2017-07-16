var util = require("util");
module.exports = function(app) {
	return {
		getBookmarkById: function(id, callback){
			app.db.all("SELECT * FROM bookmark WHERE id = ?", [id], function(err, data){
				if(data){
					if(!data.length){
						callback({status: 404, message: "No registers"}, data);
					}else{
						callback(err, data[0]);
					}
				}else{
					callback(err, data);
				}
			});
		},

		getRecentBookmarks(from, to, callback){
			app.db.all(`SELECT b.id, b.name, GROUP_CONCAT(t.name) as tags FROM bookmark b
				LEFT JOIN tagBookmark tb ON tb.bookmarkId = b.id
				LEFT JOIN tag t on t.id = tb.tagId
			WHERE b.deleted = 0 AND b.visibility = 1
			GROUP BY b.id
			ORDER BY b.id DESC
			LIMIT ?,?`, [from, to], callback);
		},

		/*
			traz o bookmark por id e traz o bookmark anterior e o proximo a este
			traz apenas bookmarks que sejam publicos e nao deletados
		 */
		getBookmarkByIdWithNavigation: function(id, callback){
			app.db.all(`SELECT id, name, visibility, html FROM bookmark
					WHERE id IN(
							(SELECT max(id) FROM bookmark WHERE id < ? AND deleted = 0 AND visibility = 1),
							?,
							(SELECT min(id) FROM bookmark WHERE id > ? AND deleted = 0 AND visibility = 1)
					)
					AND deleted = 0 AND visibility = 1;`, [id, id, id], function(err, data){

				console.debug('m=getBookmarkByIdWithNavigation, bkid=%d, err=%s', id, err)
				var foundId = -1;
				data.map((v, i) => {
					if(v.id == id){
						foundId = i;
					}
				});
				if(foundId == -1){
					data = [];
				}
				if(data){
					if(!data.length){
						callback({status: 404, message: "No registers"}, data);
					}else{
						callback(err, {
							prev: data[foundId-1],
							bookmark: data[foundId],
							next: data[foundId+1]
						});
					}
				}else{
					callback(err, data);
				}
			});
		},
		updateBookmark: function(bookmark, callback){
			console.debug('m=updateBookmark, status=begin, bookmark=%j', bookmark);
			app.db.run("UPDATE bookmark SET name=?, link=?, html=?, visibility=? WHERE id=?",
				[bookmark.name, bookmark.link, bookmark.html, getVisibilityFlag(bookmark.visible), bookmark.id],
				callback);
			console.debug('m=updateBookmark, status=success');
		},
		deleteBookmark: function(bookmarkId, callback){
			app.db.run("UPDATE bookmark SET deleted=1 WHERE id=?", [bookmarkId], callback);
		},
		recoverBookmark: function(bookmarkId){
			app.c.info("recuperando", bookmarkId);
			app.db.run("UPDATE bookmark SET deleted=0 WHERE id=?", [bookmarkId]);
		},
		deleteBookmarkPermanently: function(bookmarkId, callback){
			app.db.run("DELETE FROM tagBookmark WHERE bookmarkId = ?", [bookmarkId], function(err){
				
				if(!err)
					app.db.run("DELETE FROM bookmark WHERE id = ?", [bookmarkId], callback);
				else
					callback(err);
			});
		},
		searchBookmarksByNameOrHTML: function(query, indice, callback){
			app.db.serialize(function(){
				app.db.all(
					"\
					WITH FILTER AS ( \
						SELECT DISTINCT(id), * FROM ( \
					    SELECT b.* FROM bookmark b \
					    LEFT JOIN (\
					      SELECT t.name as tagName, tb.* FROM tagBookmark tb \
					      INNER JOIN tag t \
					      ON t.id = tb.tagId \
					  	) tags \
							ON tags.bookmarkId = b.id \
			 				WHERE deleted=0 AND ( name LIKE $query OR html LIKE $query ) \
						) \
					) \
					SELECT id, name, link, (SELECT COUNT(id) FROM FILTER) AS length, html FROM FILTER LIMIT $indice, $indiceLimite\
					",
					{
						"$query": '%' + query + '%',
						"$indice": indice,
						"$indiceLimite": indice + 100
					},
					callback);
			});
		},
		searchBookmarksByTagAndNameOrHTML: function(data, callback){
			data.indice = data.indice || 0;
			app.db.serialize(function(){
				app.db.all(
					"\
					WITH FILTER AS ( \
						SELECT DISTINCT(id), * FROM ( \
					    SELECT b.* FROM bookmark b \
					    LEFT JOIN (\
					      SELECT t.name as tagName, tb.*, t.slug FROM tagBookmark tb \
					      INNER JOIN tag t \
					      ON t.id = tb.tagId \
					  	) tags \
							ON tags.bookmarkId = b.id \
			 				WHERE slug = $tag AND b.deleted=0  \
						) \
					) \
					SELECT id, name, link, (SELECT COUNT(id) FROM FILTER) AS length, html FROM FILTER LIMIT $indice, $indiceLimite\
					",
					{
						"$indice": data.indice,
						"$indiceLimite": data.indice + 100,
						"$tag": data.tag
					},
					callback);
			});
		},
		getBookmarks: function(indice, callback){
			app.db.serialize(function(){
				app.db.all("WITH LIST AS (SELECT * FROM bookmark) \
				  SELECT id, name, link, (SELECT COUNT(id) FROM LIST) as length, html, visibility \
					FROM LIST WHERE deleted=0 LIMIT $indice, $indiceLimite",
					{
						"$indice": indice,
						"$indiceLimite": indice + 100
					},
					callback);
			});
		},
		associateTagsToBookmarkById: function(callback, bookmarkId, tags){
			if(!tags.length){
				setTimeout(callback(null));
				return ;
			}
			var sql = util.format(
			"INSERT INTO tagBookmark (tagId, bookmarkId) \n\
		    SELECT * FROM (	%s ) as rel \n\
		    WHERE NOT EXISTS ( \n\
	        SELECT 1 FROM tagBookmark t2 WHERE t2.tagId = rel.tagId AND t2.bookmarkId = rel.bookmarkId \n\
		    )", mapearTagBookmarkInserir(tags, bookmarkId)
			);
			app.db.run(sql, callback);
		},
		dessociateTagsToBookmarkById: function(bookmarkId){
			console.debug('m=dessociateTagsToBookmarkById, status=begin, bookmarkId=%d', bookmarkId)
			app.db.run('DELETE FROM tagBookmark WHERE bookmarkId=? ', [bookmarkId]);
			console.debug('m=dessociateTagsToBookmarkById, status=success')
		},
		getLasInsertedBookmarkId: function(callback){
			app.db.get("SELECT last_insert_rowid() as id ", callback);
		},
		insertBookmark: function(bookmark, callback){
			app.db.run(
				"INSERT INTO bookmark (name,link,html,visibility) VALUES (?,?,?,?)",
				[bookmark.name, bookmark.link, bookmark.html, getVisibilityFlag(bookmark.visible)],
			 	callback
		 	);
		},
		rollback: function(){
			app.db.exec("ROLLBACK");
		}
	};
};

function mapearTagBookmarkInserir(tags, bookmarkId){
	return tags.map(function(tag, i, array){
		return util.format("\nSELECT %d as tagId, %d as bookmarkId", tag.id, bookmarkId);
	}).join(" UNION ");
}

function getVisibilityFlag(visibility){
	var flag = 0;
	if(visibility == "on"){
		flag = 1;
	}
	console.debug('M=getVisiblityFlag, flag=%d', flag);
	return flag;
}