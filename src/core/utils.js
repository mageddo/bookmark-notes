var url = require('url');
module.exports = {
	fullUrl(req) {
		return url.format({
			protocol: req.protocol,
			host: req.get('host'),
			pathname: req.originalUrl
		});
	},
	getURL(req, path){

		var fullUrl = url.format({
			protocol: req.header('X-Forwarded-Protocol') || req.protocol,
			host: req.get('host'),
			pathname: path
		});
		return fullUrl

	}
}