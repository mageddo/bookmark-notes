var fs = require('fs'), auth = require('http-auth'),
		connector = require("./SqliteConnector");
module.exports = function(app){
	process.app = app;

	// se a variável não estiver setada ou setada com o valor debug então é modo debug
	app.debug = process.debug = !process.env['MG_MODE'] || process.env['MG_MODE'] === 'debug';
	setupLogger();
	console.info('Rodando em modo: ', process.env['MG_MODE']);

// system faltal error
	process.on('uncaughtException', function (error) {
		try{
			process.c.error("The system has crashed on async: %s", error.stack);
		}catch(e){}
	});

// autentication
var whitelist = [];
/**
 * Returns null if success or message if error
 */
app.whitelist = function(regex){

	if(!regex instanceof RegExp){
		return "Not a valid regex object: " + regex;
	}
	whitelist.push(regex);
}

var authPath = process.cwd() + "/conf/users.htpasswd";
if(!fs.existsSync(authPath)){
	console.error("m=authenticator, status=auth-file-not-exists, file=" + authPath);
	process.exit(-2);
}
	var basicAuth = auth.basic({
		realm: "Mageddo Bookmarks",
		file: authPath
	});
	console.info("=authenticatior, basicAuth=%o", basicAuth)
	app.use(function(req, res, next){
//		console.info('m=basicAuth, status=begin, arguments=', req.path);
		for(var regex of whitelist){
			if(regex.test(req.path)){
//				console.info('m=basicAuth, status=whitelist, regex=', regex);
				next();
				return;
			}
		}
//		console.info('m=basicAuth, status=not-matches, regex=', regex);
		auth.connect(basicAuth)(req, res, next);
	});

// whitelist urls
app.whitelist(/^\/bookmark\/[0-9]+\/?.*$/);
app.whitelist(/^\/css\//);
app.whitelist(/^\/fonts\//);
app.whitelist(/^\/js\//);
app.whitelist(/^\/highlight\//);

	// setup connection
	app.db = process.db = connector.open();
	function setupLogger(){
		var logger = require('./logger')(app);
    process.c = app.c = logger;
		app.log = process.log = function(){
			app.c.info.apply(app.c, arguments);
		}
	  
	}
};

