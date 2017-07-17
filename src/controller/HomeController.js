var LOG_FILE = __dirname + "/../../logs/log.log";

module.exports.controller = function(app) {

	var m = require("../model/BookmarkModel")(app), url = require('url');

	app.get("/", function(req, res){

		var page = parseInt(url.parse(req.url, true).query.page || 1) - 1;
		m.countPublicNotDeletedBookmarks(function(err, size){
			if(err){
				return res.render('503', {
					layout: false
				});
			}

			var pageSize = 10, pages = Math.ceil(size / pageSize), startPage = 0;
			if (page + 1 > pages){
				return res.render('index', {
					layout: false,
					msg: 'No results'
				});
			} else if(page > 0){
				startPage = page * pageSize;
			}

			console.debug('m=getRecentBookmarks, start=%d, size=%d, page=%d, pages=%d', startPage, size, page, pages)
			m.getRecentBookmarks(pageSize, startPage, function(err, data){
				console.debug('m=getRecentBookmarks, err=%s, size=%d', err, data.length)
				if(err){
					return res.render('503', {
						layout: false
					});
				}
				return res.render("index", {
					nextPage: page + 2,
					hasMore: pages > page + 1,
					layout: false,
					bookmarks: data,
					toTagArray(){
						return function(render){
							if(this.tags){
								this.tags = this.tags.split(',');
							}
						}
					 }
				});
			})

		})

	});

	app.get("/_/bookmarks", function(req, res){
		if(req.headers.layout != undefined)
			res.render("bookmarkHome", {
				compile: false,
			 	layout: false
			});
		else
			res.render("bookmarkHome", {
				compile: false,
				debug: process.debug
			});
	});

	app.get("/logs", function(req, res){
		var fs = require('fs');
		res.writeHead(200, {
		 "Content-Type" : "application/json", "Access-Control-Allow-Origin": "*" 
		});

		// criando o arquivo de logs se ainda não existir
		if(!fs.existsSync(LOG_FILE)){
			fs.closeSync(fs.openSync(LOG_FILE, 'w'));
		}

		var rl = require('readline').createInterface({
		  input: fs.createReadStream(LOG_FILE)
		});
		rl.on('line', function (line) {
		  res.write(line);
		  res.write('\n');
		});
		rl.on('close', function (line) {
		  res.end();
		});
	});

};