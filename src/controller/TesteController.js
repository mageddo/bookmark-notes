module.exports.controller = function(app){
	app.get("/nova-grid", function(req, res){
		res.render('test/nova-grid');
	});
}