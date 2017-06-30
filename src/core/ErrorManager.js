module.exports = function(app){
	return {
		_500: function(options){
			var res = {
				code: 500,
				message: options.message
			};
			if(options.res){
				options.res.set('catch', '1');
				options.res.send(res, 500);
			}
			delete options.res;
			app.c.error("%d - %j", res.code, options);
			return res;
		},
		_400: function(options){
			var res = {
				code: 400,
				message: options.message
			};
			if(options.res){
				options.res.set('catch', '1');
				options.res.send(res, 400);
			}
			delete options.res;
			app.c.info("%d - %j", res.code, options);
			return res;
		}

	}
};