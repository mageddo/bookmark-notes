module.exports = function(app) {
	return {
		cadTest: function(){
			app.db.run("INSERT INTO bookmark (id) values (?) ", [1]);
		}
	}
};
