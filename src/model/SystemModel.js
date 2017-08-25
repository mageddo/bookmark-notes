module.exports = function(app){

	return {

		getSystemVersion(callback){
			this.getSystemProperty('DB_VERSION', (version) => {
				callback(version ? parseFloat(version) : 1.6);
			});
		},

		getSystemProperty(name, callback){

			app.db.each("SELECT DES_VALUE AS value FROM SYSTEM_PROPERTY WHERE NAM_PROPERTY = ?", name, (err, systemProperty) => {
				if(err != null){
					console.warn('m=getSystemProperty, err=%s', err.stack);
					app.db.each("SELECT value FROM systemProperty WHERE name = ?", name, (err, systemProperty) => {
						callback(systemProperty ? systemProperty.value : null);
					});
				}else{
					callback(systemProperty ? systemProperty.value : null);
				}
			});

		}

	}

}
