var util = require("util");
module.exports = function(app){
	return {
		getTagsByBoomarkId: function(bookmarkId, callback){
			app.db.all(`
				SELECT t.idt_tag id, t.NAM_TAG as name, t.COD_SLUG as slug, tb.idt_bookmark bookmarkId FROM tag t
				LEFT JOIN tag_Bookmark tb
				ON t.idt_tag = tb.idt_tag
				WHERE tb.idt_bookmark = ?`, [bookmarkId], callback);
		},
		searchTagsByName: function(queryName, callback){
			app.db.all(`
				SELECT t.idt_tag id, t.NAM_TAG as name, t.COD_SLUG as slug, tb.idt_bookmark bookmarkId
				FROM tag WHERE nam_tag LIKE ?
			`, ['%' + queryName + '%'], callback);
		},
		insertTag: function(tag, callback){
			tag.slug = this.toSlug(tag.name);
			if(!tag.slug){
				callback("Pass a valid tag");
			}else{
				app.db.run("INSERT INTO tag (nam_tag, cod_slug) VALUES ($name, $slug)", mapName(tag), callback);
			}

		},
		getTagsBySlugs: function(callback, slugs){
			var tagsParams = slugs.map(function(){
			 return "?";
			}).join(",");
			app.db.all("SELECT * FROM TAG WHERE cod_slug in (" + tagsParams + ")", slugs, callback);
		},
		mergeTag: function(tags, callback){
			_tags = tags;
			tags = this.validateTags(tags);
			if(tags.length != _tags.length){
				setTimeout(callback.bind(null, "Existem tags inválidas"), 1);
				return ;
			}else if(!tags.length){
				setTimeout(callback.bind(null, null, []));
				return ;
			}

			var that = this,
			sql = util.format(
				"INSERT INTO tag (nam_tag, cod_slug) \n\
				SELECT name, slug FROM( \n\
					%s \n\
				) as tags \n\
				WHERE NOT EXISTS ( \n\
			    SELECT 1 FROM tag t2 WHERE t2.slug = tags.slug \n\
				);", mapearTagsInserir(tags)
			);
			app.db.run(sql,function(err, rows){
					if(!err){
						that.getTagsBySlugs(callback, tags.map(function(tag){
							return tag.slug;
						}));
						return ;
					}
					callback(err, tags);
			});
		},
		validateTags: function(tags){
			var filtered = [],
					that = this;
			tags.map(function(tag){
				tag.name = tag.name.trim();
				tag.slug = that.toSlug(tag.name);
				if(tag.slug)
					filtered.push(tag);
			});
			return filtered;
		},
		/**
		 * converte uma string irregular para um formato de chave aceito
		 */
		toSlug: function(name){
			return name.toLocaleLowerCase().replace(/[^a-z0-9\ \-_]+/g, "").trim().replace(/[\ _]+/g, "\-");
		},
		getTags: function(callback){
			app.db.all("SELECT idt_tag as id, nam_tag as name, cod_slug as slug FROM tag ORDER BY nam_tag", [], callback);
		}
	};
};

function mapearTagsInserir(tags){
	return tags.map(function(tag, i, array){
		return util.format("\nSELECT '%s' as name, '%s' as slug", tag.name, tag.slug);
	}).join(" UNION ");
}

function mapName(o){
	var isArray = !Array.isArray(o);
	if(isArray)
		o = [o];

	var result = [], n;
	o.map(function(item){
		console.log(item);
		n = {};
		Object.keys(item).map(function(value, index) {
		   n['$' + value ] = item[value];
		});
		result.push(n);
	});
	if(isArray)
		return result;
	return n;
}
