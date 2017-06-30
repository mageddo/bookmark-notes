module.exports = function(app){

	return {

		getSystemVersion(callback){
			this.getSystemProperty('DB_VERSION', (version) => {
				callback(version ? parseFloat(version) : 1.6);
			});
		},

		getSystemProperty(name, callback){
			app.db.each("SELECT value FROM systemProperty WHERE name = ?", name, (err, systemProperty) => {
				callback(systemProperty ? systemProperty.value : null);
			});
		}

	}

}