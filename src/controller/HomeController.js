var LOG_FILE = __dirname + "/../../logs/log.log";

module.exports.controller = function(app) {

	var m = require("../model/BookmarkModel")(app);

	app.get("/", function(req, res){
		m.getRecentBookmarks(0, 100, function(err, data){
			console.debug('m=getRecentBookmarks, err=%s, size=%d', err, data.length)
			res.render("index", {
				layout: false,
				bookmarks: data,
				toTagArray(){
					return function(render){
						console.debug('m=toTagArray');
						this.tags = this.tags.split(',');
					}
				 }
			});

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