var util = require("util");
module.exports = function(app) {
	return {
		getBookmarkById: function(id, callback){
			app.db.all(`
			SELECT
			IDT_BOOKMARK AS id, NAM_BOOKMARK AS name,
			DES_LINK AS link, DES_HTML AS html,
			FLG_DELETED AS deleted, FLG_ARCHIVED AS archived,
			NUM_VISIBILITY AS visibility
			FROM bookmark WHERE IDT_BOOKMARK = ?`, [id], function(err, data){
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

		getRecentBookmarks(pageSize, offset, callback){
			app.db.all(`SELECT b.IDT_BOOKMARK as id, b.NAM_BOOKMARK as name, GROUP_CONCAT(t.NAM_TAG) as tags FROM BOOKMARK B
				LEFT JOIN TAG_BOOKMARK TB ON TB.IDT_BOOKMARK = B.IDT_BOOKMARK
				LEFT JOIN TAG T ON T.IDT_TAG = TB.IDT_TAG
			WHERE B.FLG_DELETED = 0 AND B.num_visibility = 1
			GROUP BY B.IDT_BOOKMARK
			ORDER BY B.ID_BOOKMARK DESC
			LIMIT ?,?`, [offset, pageSize], callback);
		},

		countPublicNotDeletedBookmarks(callback){
			app.db.each(`SELECT COUNT(1) AS COUNT FROM bookmark b
				WHERE b.flg_deleted = 0 AND b.num_visibility = 1`, function(err, data){
					callback(err, !err ? data['COUNT'] : null)
				})
		},

		/*
			traz o bookmark por id e traz o bookmark anterior e o proximo a este
			traz apenas bookmarks que sejam publicos e nao deletados
		 */
		getBookmarkByIdWithNavigation: function(id, callback){
			app.db.all(`SELECT idt_bokmark as id, nam_boookmark as name, num_visibility as visibility, des_html as html FROM bookmark
					WHERE idt_bokmark IN(
							(SELECT max(id_bookmark) FROM bookmark WHERE idt_bookmark < ? AND flg_deleted = 0 AND num_visibility = 1),
							?,
							(SELECT min(idt_bokmark) FROM bookmark WHERE idt_bokmark > ? AND flg_deleted = 0 AND num_visibility = 1)
					)
					AND flg_deleted = 0 AND num_visibility = 1;`, [id, id, id], function(err, data){

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
			app.db.run("UPDATE bookmark SET nam_bookmark=?, des_link=?, des_html=?, num_visibility=? WHERE idt_bookmark=?",
				[bookmark.name, bookmark.link, bookmark.html, getVisibilityFlag(bookmark.visible), bookmark.id],
				callback);
			console.debug('m=updateBookmark, status=success');
		},
		deleteBookmark: function(bookmarkId, callback){
			app.db.run("UPDATE bookmark SET flg_deleted=1 WHERE idt_bookmark=?", [bookmarkId], callback);
		},
		recoverBookmark: function(bookmarkId){
			app.c.info("recuperando", bookmarkId);
			app.db.run("UPDATE bookmark SET flg_deleted=0 WHERE idt_bookmark=?", [bookmarkId]);
		},
		deleteBookmarkPermanently: function(bookmarkId, callback){
			app.db.run("DELETE FROM TAG_BOOKMARK WHERE IDT_BOOKMARK = ?", [bookmarkId], function(err){
				
				if(!err)
					app.db.run("DELETE FROM bookmark WHERE idt_bookmark = ?", [bookmarkId], callback);
				else
					callback(err);
			});
		},
		searchBookmarksByNameOrHTML: function(query, indice, callback){
			app.db.serialize(function(){
				app.db.all(
					`
					WITH FILTER AS (
						SELECT DISTINCT(idt_bookmark), * FROM (
							SELECT b.* FROM bookmark b
							LEFT JOIN (
								SELECT t.NAM_TAG as tagName, tb.* FROM tag_Bookmark tb
								INNER JOIN tag t ON t.idt_tag = tb.idt_tag
							) tags
							ON tags.idt_bookmark = b.idt_bookmark
								WHERE flg_deleted=0 AND ( nam_bookmark LIKE $query OR des_html LIKE $query )
						)
					)
					SELECT
						idt_bookmark as id, nam_bookmark as name,
						des_link as link, (SELECT COUNT(idt_bookmark) FROM FILTER) AS length,
						des_html as html
						FROM FILTER LIMIT $indice, $indiceLimite
					`,
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
					`
					WITH FILTER AS (
						SELECT DISTINCT(idt_bookmark), * FROM (
							SELECT b.* FROM bookmark b
							LEFT JOIN (
								SELECT t.nam_tag as tagName, tb.*, t.cod_slug FROM tag_Bookmark tb
								INNER JOIN tag t ON t.idt_tag = tb.idt_tag
							) tags ON tags.idt_bookmark = b.idt_bookmark
							WHERE tags.COD_SLUG = $tag AND b.flg_deleted=0
						)
					)
					SELECT
						idt_bookmark as id, nam_bookmark as name,
						des_link as link, (SELECT COUNT(idt_bookmark) FROM FILTER) AS length,
						des_html as html
						FROM FILTER LIMIT $indice, $indiceLimite
					`,
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
				app.db.all(`WITH LIST AS (
						SELECT * FROM bookmark
					)
					SELECT
					idt_bookmark as id, nam_bookmark as name,
					num_visibility as visibility, des_html as html,
					(SELECT COUNT(idt_bookmark) FROM LIST) as length
					FROM LIST WHERE flg_deleted=0 LIMIT $indice, $indiceLimite`,
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
			"INSERT INTO tag_Bookmark (idt_tag, idt_boookmark) \n\
				SELECT * FROM (	%s ) as rel \n\
				WHERE NOT EXISTS ( \n\
				SELECT 1 FROM tag_Bookmark t2 WHERE t2.idt_tag = rel.idt_tag AND t2.idt_bookmark = rel.idt_bookmark \n\
		    )", mapearTagBookmarkInserir(tags, bookmarkId)
			);
			app.db.run(sql, callback);
		},
		dessociateTagsToBookmarkById: function(bookmarkId){
			console.debug('m=dessociateTagsToBookmarkById, status=begin, bookmarkId=%d', bookmarkId)
			app.db.run('DELETE FROM tag_Bookmark WHERE idt_bookmark=? ', [bookmarkId]);
			console.debug('m=dessociateTagsToBookmarkById, status=success')
		},
		getLasInsertedBookmarkId: function(callback){
			app.db.get("SELECT last_insert_rowid() as id ", callback);
		},
		insertBookmark: function(bookmark, callback){
			app.db.run(
				"INSERT INTO bookmark (nam_bookmark,des_link,des_html,num_visibility) VALUES (?,?,?,?)",
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
