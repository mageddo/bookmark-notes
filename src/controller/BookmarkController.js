var url = require("url"), util = require('util'), fs = require('fs'), utils = require('../core/utils');
var marked = require('../../public/js/marked.min.js');
var hljs = require('highlight.js');
var mustache = require('mustache');
var PAGE_SIZE = 100

module.exports.controller = function(app) {

	var m = require("../model/BookmarkModel")(app);
	var mTag = require("../model/TagModel")(app);
	var config = require('config');
	var request = require('request')

	app.put('/api/bookmark', function(req, res) {
		app.db.serialize(function(){
			app.db.exec("BEGIN");
			m.insertBookmark(req.body, function(err){
				if(err){
					app.log("m=insertBookmark, status=error, err=%j", err);
					app.em._500({
						message: "Não foi possível cadastrar o bookmark",
						stacktrace: err,
						res: res
					});
					m.rollback();
					return ;
				}
				app.log("bookmark cadastrado, associando as tags..");
				associarTagsBookmarks(app,req, res, this.lastID);
			});
		});
	});

	app.get('/api/memory', function(req, res){
		var m = process.memoryUsage();
		//console.log(m);
		mbytes = (m.heapUsed / 1024.0 / 1024.0);
		res.end(mbytes + '');
	});

	app.delete('/api/bookmark', function(req, res) {
		app.db.serialize(function(){
			app.db.exec("BEGIN");
			app.c.info("m=delete, status=begin, bookmark=%s", req.body.id);
			m.deleteBookmark(req.body.id, function(err){
				if(err){
					app.em._500({
						res: res,
						stacktrace: err,
						message: "Não foi possível deletar o bookmark"
					})
					m.rollback();
				}else{
					app.c.info("m=delete, status=success, bookmark=%s", req.body.id);
					app.db.exec("COMMIT");
					res.end();
				}
			});
		});
	});

	app.post('/api/bookmark', function(req, res) {

		if(!req.body.id || !parseInt(req.body.id) > 0){
			res.writeHead(500);
			res.end("informe o id");
			 return ;
		}
		var id = req.body.id;
		app.db.serialize(function(){
			app.db.exec("BEGIN");
			m.updateBookmark(req.body, function(err){
				if(err){
					app.log("m=updateBookmark, status=error, err=%j", err);
					app.em._500({
						message: "Não foi possível atualizar o bookmark",
						stacktrace: err,
						res: res
					});
					m.rollback();
					return ;
				}
				console.log("bookmark atualizado")
				associarTagsBookmarks(app,req, res, id, true);
			});
		});
	});

	app.post('/api/bookmark/recover', function(req, res) {
		var id;
		if((id=parseInt(req.body.id)) < 0 || Number.isNaN(id)){
			app.em._400({
				message: "É necessário o id do bookmark para efetuar a restauração",
				res: res
			});
			return ;
		}
		app.db.serialize(function(){
			try{
				app.db.exec("BEGIN");
				m.recoverBookmark(id);
				app.db.exec("COMMIT");
				res.end();
			}catch(e){
				app.em._500({
					message: "Não foi possível recuperar o bookmark",
					stacktrace: e.stack,
					res: res
				});
				m.rollback();
			}
		});
	});


	app.get('/api/bookmark', function(req, res) {

		var from = url.parse(req.url, true).query.indice || 0;
		var apiURL = config.get('api.url');
		var restURL = apiURL + '/api/v1.0/bookmark?from=' + from + '&quantity=' + PAGE_SIZE

		request.get({url: restURL, json: true}, function (err, response, body) {
			console.info('M=GET /api/bookmark, error=%s, code=%d', err, response && response.statusCode);
			if(err != null || response.statusCode != 200){
				console.info('M=GET /api/bookmark, error=%s, code=%d, body=%j', err, response && response.statusCode, body);
				app.em._500({
					res: res,
					message: (body && body.message) || "Could not get bookmarks temporally",
					stacktrace: err
				});
				return ;
			} else {
				res.header('Content-Type', 'application/json')
				res.send(body);
			}
		});

	});

	app.get('/api/bookmark/search', function(req, res) {

		var query = url.parse(req.url, true).query;
		var apiURL = config.get('api.url');
		var restURL = util.format(
			'%s/api/v1.0/bookmark?from=%d&quantity=%d&tag=%s&query=%s',
			 apiURL, query.indice || 0, PAGE_SIZE, query.tag || '', query.query || ''
		);

		request.get({url: restURL, json: true}, function (err, response, body) {
			console.info('M=GET /api/bookmark/search, error=%s, code=%d', err, response && response.statusCode);
			if(err != null || response.statusCode != 200){
				console.error('M=GET /api/bookmark/search, status=error, error=%s, code=%d, body=%j', err, response && response.statusCode, body);
				app.em._400({
					res: res,
					message: (body && body.message) || "Could not get bookmarks temporally",
					stacktrace: err
				});
			} else {
				res.header('Content-Type', 'application/json')
				res.send(body);
			}
		});
	});

	app.get('/api/bookmark/:id', function(req, res) {
		m.getBookmarkById(req.params.id, function(err, data){
			res.send(data);
		});
	});

	app.get('/bookmark/new', function(req, res) {
		res.render('bookmarkEdit', {
			layout: false,
			stringify: function(){
				return function(){
					return '[]';
				}
			},
			getVisibilityFlag: getVisibilityFlag
		});
	});

	/**
	 * Public bookmark view
	 */
	app.get('/bookmark/:id/:description', function(req, res) {
		console.info('m=/bookmark/:id/:description, status=begin, id=%s, desc=%s', req.params.id, req.params.description);
		m.getBookmarkByIdWithNavigation(req.params.id, (err, bookmark) => {

			console.debug("M=getPublicBookmark, status=loaded, bookmark=%j", bookmark.bookmark.id);
			var content, title, id;
			if(bookmark.bookmark != null){
					id = bookmark.bookmark.id;
					title = bookmark.bookmark.name;

					// if you make changes here probably you also want to change  public/js/ct/bookmarkEdit.js#267
					content = parseCode(`
					<div class="mg-code" lang="{{lang}}">
						<ul class="nav nav-pills painel-acoes">
							<li role="presentation" style="visibility: hidden;" class="pull-right" ><a href="#" class="skipped glyphicon glyphicon-option-vertical"></a></li>
						</ul>
						<pre><code>{{{code}}}</code></pre>
					</div>`, bookmark.bookmark.html);
			}else{
				title = util.format("Bookmark '%s' not found", req.params.description || req.params.id);
				content = "";
			}

			res.render('bookmarkView', {
				analytics: config.get('analytics.id'),
				creationDate: toSQLDate(bookmark.bookmark.creationDate),
				updateDate: toSQLDate(bookmark.bookmark.updateDate),
				prev: bookmark.prev,
				next: bookmark.next,
				layout: 'publicLayout',
				id: id,
				title: title,
				content: content,
				description: utils.clearHTML(content).replace(/[\r\n]+/, "").substring(0, 160),
				encode: function(){
					return function(name, parser){
						return encodeURIComponent(parser(name));
					}
				},
				getURL(){
					return function(path, render){
						return utils.getSEOURL(req, render(path))
					};
				}
			});

		});
	});

	app.get('/bookmark/edit', function(req, res) {
		var query = url.parse(req.url, true).query;
		m.getBookmarkById(query.id, function(err, bookmark){
			mTag.getTagsByBoomarkId(query.id, function(err, tags){
				res.render('bookmarkEdit', {
					layout: false,
					bookmark: bookmark,
					tags: tags,
					editMode: query.editMode,
					stringify: function(){
						return function(val, render){
							return JSON.stringify(this.tags.map(function(tag){
								return tag.name;
							}));
						}
					},
					getVisibilityFlag: getVisibilityFlag
				});
			});
		});
	});

	function associarTagsBookmarks(app,req, res,id, editMode){
		if(editMode){
			try{
				app.log("m=associarTagsBookmarks, status=deletando tags antigas, id=%d, editMode=%s", id, editMode);
				m.dessociateTagsToBookmarkById(id);
			}catch(e){
				app.em._500({
					message: "Não foi possível desassociar o bookmark com as tags",
					res: res,
					stacktrace: e.stack
				});
				m.rollback();
				return ;
			}
		}
		if(!Array.isArray(req.body.tag)){
			app.c.debug("Sem tags para serem cadastradas");
			res.end(id + '');
			app.db.exec("COMMIT");
			return ;
		}
		app.log("existem tags para serem cadastradas");
		var tagsToCad = toTags(req.body.tag);
		app.log("cadastrando tags que ainda não o foram..");
		mTag.mergeTag(tagsToCad, function(err, tags){
			if(err){
				console.error('m=mergeTag, error=%s', err);
				app.em._500({
					message: "Não foi possível cadastrar as novas tags",
					res: res,
					stacktrace: err
				});
				m.rollback();
				return;
	  	}
			app.log("tags foram cadastradas, as relacionadas a esse bookmark são...", tags.map(t => t.slug));
			app.log("associando as novas");
			m.associateTagsToBookmarkById(function(err){
				if(err){
					console.error('m=associateTagsToBookmarkById, error=%s', err);
					app.em._500({
						message: "Não foi possível associar o bookmark com as tags",
						res: res,
						stacktrace: err
					});
					m.rollback();
					return ;
				}
				try{
					app.db.exec("COMMIT");
				}catch(e){
					app.em._500({
						message: "Não foi possível efetuar o cadastro do bookmark, tente novamente mais tarde" ,
						res: res,
						stacktrace: e
					});
					app.db.rollback();
				}
				res.end(id + '');
			}, id, tags);
	  });
	}
};

var languagesMap = {};
hljs.listLanguages().forEach(lang => languagesMap[lang] = true)
function parseCode(template, content){
	var renderer = new marked.Renderer();
	renderer.code = function(code, lang){
		var parsedCode = languagesMap[lang] ? hljs.highlight(lang, code) : hljs.highlightAuto(code);
		return mustache.render(template, {lang: lang, code: parsedCode.value, overflown: parsedCode.value.split(/\r\n|\r|\n/).length > 7 });
	}
	renderer.table = function(header, body) {
		return '<table class="table table-bordered table-striped">\n'
			+ '<thead>\n'
			+ header
			+ '</thead>\n'
			+ '<tbody>\n'
			+ body
			+ '</tbody>\n'
			+ '</table>\n';
	}
	return marked(content || '', {
		renderer: renderer
	})
}

function toTags(tags){
	return tags.map(function(tagName){
		return {
			name: tagName
		};
	});
}

function getVisibilityFlag(){
	return function(number, render){
		number = render(number);
		console.debug('m=getVisibilityFlag, number=%s', number);
		if(number == 1){
			return " checked";
		}
		return "";
	}
}

function toSQLDate(d){
	if(d){
		return d.substring(0, d.length-3)
	}
	return ''
}
