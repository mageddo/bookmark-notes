var express = require('express'),
	app = express(),
	http = require('http'),
	path = require('path'),
	bodyParser = require('body-parser'),
	mustacheLayout = require('mustache-layout'),
	fs = require('fs'),
	conf = require("./src/core/Setup")(app),
	em = require("./src/core/ErrorManager")(app);

// some environment variables
app.set('port', process.env.PORT || 3000);
app.set('views', './view');
app.set('view engine', 'html');
app.set("view options", {layout: true});
app.em = em;

//app.use(express.favicon());
// app.use(express.logger('dev'));
mustacheLayout.debug(false);
app.engine("html", mustacheLayout);
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(express.methodOverride());
app.use(express.cookieParser('your secret here'));
app.use(express.session());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

app.locals({
	'appName': "Bookmarks"
});

// dynamically include routes (Controller)
try{
	fs.readdirSync('./src/controller').forEach(function (file) {
		if(file.substr(-3) === '.js') {
			process.c.debug("loading: %s", file);
			route = require('./src/controller/' + file);
			route.controller(app);
		}
	});
}catch(e){
	process.c.error("erro fatal sistema: ", e.stack);
}
app.listen(app.get('port'), function(){
	process.c.log('Express server listening on port ' + app.get('port'));
});
