/**
 * Cria e fecha conexões com o banco de dados
 * @type @call;require@call;verbose
 */
const sqlite3 = require('sqlite3').verbose(),
			fs = require('fs');
var con;
module.exports = {
	version: "1.7",
	close: function(db){
		try{
			db.close();
			console.log("conexão banco fechada");
		}	catch(e){};
	},
	get: function(){
		console.log("retorna ", con);
		return (con && !con.open) || this.open();
	},
	open: function(){
		var file = process.cwd() + '/db/bookmarks.db';
		console.log("abrindo conexão...");
		con = new sqlite3.Database(file);
		if(!fs.existsSync(file)){
			console.log('arquivo do banco de dados nao existe', file);
			buildDatabase(con, 0);
		}
		var currentVersion = this.version;
		console.info("m=open, status=connected, db=%s, currentVersion=%d", file, currentVersion);
		require('../model/SystemModel')({db: con}).getSystemVersion(version => {
			console.info("m=getSystemVersionCb, dbversion=%d, currentVersion=%d", version, currentVersion);
			if(version < this.version){
				console.info("m=getSystemVersionCb, status=buildingFrom, version=%d", version);
				buildDatabase(con, version);
			}
		})


		return con;
	}
};

function buildDatabase(con, fromVersion){
	var versions = getDBSQL(fromVersion);
	console.info('m=buildDatabase, status=get-versions');
	versions.forEach(v => {
		console.info('m=buildDatabase, status=executing, version=%d', v.version);
		con.exec(v.sql);
		console.info('m=buildDatabase, status=executed');
	});
}

function getDBSQL(fromVersion){

	console.info('m=getDBSQL, status=begin, fromVersion=%d', fromVersion);

	const REGEX_VALID_SQL_FILE_VERSION = /^([0-9.]+)\.sql$/,
			folder = process.cwd() + '/files/prod/';
	var files = fs.readdirSync(folder);
	var r = [];
	files.forEach(file => {
		console.info('m=getDBSQL, status=read-file, file=%s', file);
		var result;
		if((result = REGEX_VALID_SQL_FILE_VERSION.exec(file)) == null){
			return;
		}

		var sql = {
			version: parseFloat(result[1]),
			file: file,
			sql: fs.readFileSync(folder + '/' + file, "utf-8")
		};

		console.info('m=getDBSQL, status=parsed, result=%j, sqlVersion=%d', result, sql.version);
		if(sql.version > fromVersion){
			r.push(sql);
		}
	});
	console.info('m=getDBSQL, status=success, size=%d', r.length);
	return r;

}