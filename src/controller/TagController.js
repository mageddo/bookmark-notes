var url = require("url"),
		util = require("util");

module.exports.controller = function(app) {
	var tagM = require("./../model/TagModel")(app);
	app.get('/api/tag/search', function(req, res) {
		var queryData = url.parse(req.url, true).query;
		tagM.searchTagsByName(queryData.q, function(err, tags){
			if(err){
				console.error('m=searchTagsByName, err=%s', err);
				app.em._500({
					message: util.format("Erro ao buscar tags por: %s", queryData.q),
					stacktrace: err,
					res: res
				});
			}else{
				res.send(tags);
			}
		});
	});
	app.put('/api/tag', function(req, res) {
		tagM.insertTag(req.body, function(err, tags){
			res.send();
		});
	});
	app.get('/api/tag', function(req, res) {
		tagM.getTags(function(err, rows){
			if(err){
				app.em._500({
					message: "Opa, encontramos um problema ao carregar os bookmarks",
					stacktrace: err.stack,
					res: res
				});
			}else{
				res.send(rows);
			}
		});
	});

};
