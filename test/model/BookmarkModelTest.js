var assert = require("assert");
var app = {};
app.db = require("./../../src/core/SqliteConnector").open();
var bm = require("./../../src/model/BookmarkModel")(app);


// success
bm.getBookmarkById(4, function(err, data){
	console.log("ok: id encontrado sucesso", assert(4, data.id));
});

// not found
bm.getBookmarkById(-1, function(err, data){
	assert(404, err.status)
	console.log("ok: id nao encontrado test");
});

describe('bookmark model test', function() {

	describe('associateTagsToBookmarkByIdSuccess', function(){
	  it('associateTagsToBookmarkById associando', function(done){
  		bm.associateTagsToBookmarkById(function(err, items){
				console.log(err, items);
				assert(!err && !items, "Erro na associação de tags com o bookmark");
				done();
  		},3 , [{id:1},{id:2},{id:3}]);
	  });
	});

	describe('searchBookmarkByNameOrHTML', function(){
	  it('bookmarks buscados', function(done){
  		bm.searchBookmarkByNameOrHTML("a", function(err, items){
				assert(!err, "Erro na busca de bookmarks");
				assert(items != null, "Não foram retornados items");
				done();
  		});
	  });
	});

	describe('inserindo  bookmark', function(){
	  it('bookmark inserido', function(done){
  		bm.insertBookmark({name: "bookmark de mock"}, function(err){
  			console.log(err);
  			assert(!err, "Erro na inserção do bookmark");
  			done();
  		});
	  });
	  it('id do ultimo inserido resgatado', function(done){
			bm.getLasInsertedBookmarkId(function(err, lastBookmark){
				var id = lastBookmark.id;
				assert(!err, "A query obteve erros");
				assert(id !== 0, "id não foi encontrado");
		  	console.log("foi inserido o id %d", id);
		  	done();
	  	});
	  });
	});
});

