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
	getSEOURL(req, path){
		return this.getURL(req, path.replace(/\s/g, '-').toLowerCase())
	},
	clearHTML(html){
		return html.replace(/<\/?[^>]+(>|$)/g, "");
	}
}
