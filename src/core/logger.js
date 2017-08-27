function logger(app){
	var winston = require('winston'),
			dateformat = require("date-format"),
			util = require('util');

	// Set up logger
	var customColors = {
		trace: 'white',
		debug: 'white',
		info: 'blue',
		warn: 'yellow',
		error: 'red',
		severe: 'purple'
	};
	var levels = {
		trace: 0,
		debug: 1,
		info: 2,
		warn: 3,
		error: 4,
		severe: 5
	};
	var logger = new(winston.Logger)(getConfig(app.debug));
	winston.addColors(customColors);


	// fixing string interpolation
	var levels = ["trace","debug", "info", "warn", "error", "severe"];
	levels.map(function(level){
		logger['_' + level] = logger[level];
		logger[level] = function(){
			this['_' + level](util.format.apply(this, arguments));
		}
	});

	// configurando no logger padrao
	console.log = logger.info.bind(logger);
	console.debug = logger.debug.bind(logger);
	console.info = logger.info.bind(logger);
	console.warn = logger.warn.bind(logger);
	console.error = logger.error.bind(logger);

	function getConfig(debug){
		if(debug){
			return {
				// colors: customColors,
				// levels: levels,
				transports: [
					new(winston.transports.Console)({
						level: "debug",
						colorize: true,
						timestamp: function(){
							return dateformat.asString('yyyy-MM-dd hh:mm:ss:SSS', new Date());
						}
					})
				]
			};
		}else{
			return {
				// levels: levels,
				transports: [
					new (winston.transports.File)({
						level: "info",
						filename: 'logs/log.log',
						timestamp: function(){
							return dateformat.asString('yyyy-MM-dd hh:mm:ss:SSS', new Date());
						},
						json: true,
						stringify: function(log){
							return JSON.stringify(log);
						},
						maxsize: 62914560, // 60m
						zippedArchive: true
					})
				]
			};
		}
	}
	return logger;
}
module.exports = logger;
