var url = require('url');
module.exports = {
	getURL(req, path){

		var fullUrl = url.format({
			protocol: req.header('X-Forwarded-Protocol') || req.protocol,
			host: req.get('host'),
			pathname: encodeURI(path)
		});
		return fullUrl

	},
	clearHTML(html){
		return html.replace(/<\/?[^>]+(>|$)/g, "");
	}
}