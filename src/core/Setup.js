var fs = require('fs'), auth = require('http-auth'),
		connector = require("./SqliteConnector");

module.exports = function(app){
	process.app = app;

	// se a variável não estiver setada ou setada com o valor debug então é modo debug
	app.profile = process.env['NODE_ENV'] || 'dev'
	app.debug = process.debug = app.profile == 'dev';
	setupLogger();
	console.info('m=coresetup, profile=%s', app.profile);

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
	console.warn("m=authenticator, status=auth-file-not-exists, file=" + authPath);
	var originalAuthPath = process.cwd() + "/conf.default/users.htpasswd";
	fs.appendFileSync(authPath, fs.readFileSync(originalAuthPath), {flag: 'w'});
}

var basicAuth = auth.basic({
	realm: "Mageddo Bookmarks",
	file: authPath
});
console.info("m=authenticator, basicAuth=%o", basicAuth)
app.use(function(req, res, next){
	for(var regex of whitelist){
		if(regex.test(req.path)){
			next();
			return;
		}
	}
	auth.connect(basicAuth)(req, res, next);
});

// whitelist urls
app.whitelist(/^\/?$/);
app.whitelist(/sitemap\.xml/);
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

