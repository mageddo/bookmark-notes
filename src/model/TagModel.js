var util = require("util");
module.exports = function(app){
	return {
		getTagsByBoomarkId: function(bookmarkId, callback){
			app.db.all("SELECT * FROM tagView WHERE bookmarkId = ?", [bookmarkId], callback);
		},
		searchTagsByName: function(queryName, callback){
			app.db.all("SELECT * FROM tag WHERE name LIKE ?", ['%' + queryName + '%'], callback);
		},
		insertTag: function(tag, callback){
			tag.slug = this.toSlug(tag.name);
			if(!tag.slug){
				callback("Pass a valid tag");
			}else{
				app.db.run("INSERT INTO tag (name, slug) VALUES ($name, $slug)", mapName(tag), callback);
			}

		},
		getTagsBySlugs: function(callback, slugs){
			var tagsParams = slugs.map(function(){
			 return "?";
			}).join(",");
			app.db.all("SELECT * FROM TAG WHERE slug in (" + tagsParams + ")", slugs, callback);
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
				"INSERT INTO tag (name, slug) \n\
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
		 * 
		 * Retorna os ultimos elementos inseridos
		 * @param n quantidade dos ultimos elementos a inserir, 1 é default
		 * @param props os parametros a serem retornados, id é o default
		 */
		getLastInserted: function(callback, n, props){
			var DEFAULT = 1;
			props = props || "id";
			n = n || DEFAULT;
			app.db.all("SELECT "+ props +" FROM tag ORDER BY id DESC LIMIT ?", [n], function(err, rows){
				if(n == DEFAULT)
					rows = rows && rows[0];
				callback(err, rows);
			});
		},
		/**
		 * converte uma string irregular para um formato de chave aceito
		 */
		toSlug: function(name){
			return name.toLocaleLowerCase().replace(/[^a-z0-9\ \-_]+/g, "").trim().replace(/[\ _]+/g, "\-");
		},
		getTags: function(callback){
			app.db.all("SELECT id, name, slug FROM tag ORDER BY name", [], callback);
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
