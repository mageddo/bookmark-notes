var assert = require("assert");
var app = {};
app.db = require("./../../src/core/SqliteConnector").open();
var bm = require("./../../src/model/TagModel")(app);

describe('Tag', function() {
	describe('searchTagsByNameSuccess', function(){
	  it('buscado', function(done){
	  	bm.searchTagsByName("Elvis", function(err, data){
				console.log("ok: id tag encontrada com sucesso", assert(4, data[0].id));
				done();
			});
	  });
	});  
	
	describe('insertTagSuccess', function(){
	  it('inserido', function(done){
	  	bm.insertTag({ name: "Elvis Teste" }, function(err, data){
				console.log("tag cadastrada com sucesso");
				done();
			});
	  });
	});

	describe('validateTagsSuccess', function(){
	  it('convertendo para o padrao correto', function(done){
  		var r = bm.validateTags([{ name: " Tag de teste $ ! " }, {name: "teste"}]);
  		assert.equal(r[0].slug, "tag-de-teste");
  		console.log("depois" ,r);
  		done();
	  });
	});

	describe('validateTagsComNumerosSuccess', function(){
	  it('convertendo para o padrao correto', function(done){
  		var r = bm.validateTags([{ name: " Tag de teste 1$ ! 10 " }, {name: "teste"}]);
  		assert.equal(r[0].slug, "tag-de-teste-1-10");
  		console.log("depois" ,r);
  		done();
	  });
	});
	describe('validateTagsInválidaSuccess', function(){
	  it('convertendo para o padrao correto', function(done){
  		var r = bm.validateTags([{ name: "  $ ! " }, {name: "   "}]);
  		assert.equal(r.length, 0);
  		console.log("depois" ,r);
  		done();
	  });
	});

	describe('mergeTagExistenteSuccess', function(){
	  it('inserido com merge', function(done){
  		bm.mergeTag([{ name: " Tag de teste $ ! " }, { name: "outra tag"}], function(err, data){
				console.log("retornou ",err, data);
				assert(!err, "deu erro no mergeTagSuccess");
				assert(data[0].slug, "tag-de-teste");
				console.log("tag cadastrada em merge com sucesso");
				done();
			});
	  });
	});

	describe('mergeTagNovaSuccess', function(){
	  it('inserido nova com merge', function(done){
  		bm.mergeTag([{ name: Math.random().toString(36).slice(-15) }], function(err, data){
				console.log("retornou ",err, data);
				assert(!err, "deu erro no mergeTagSuccess");
				console.log("nova tag cadastrada em merge com sucesso");
				done();
			});
	  });
	});

	describe('mergeTagFail', function(){
	  it('nao pode ser feito merge porque a tag não tem caracteres válidos', function(done){
  		bm.mergeTag([{ name: "  $ ! " }], function(err, data){
				console.log("retornou ",err, data);
				assert(err);
				console.log("tag fail sucesso");
				done();
			});
	  });
	});

	describe('getLastInsertedMaisUmaLinhaSuccess', function(){
	  it('trazendo os ultimos inseridos', function(done){
  		bm.getLastInserted(function(err, items){
				console.log(items);
				done();
  		},2, null);
	  });
	});

	describe('getLastInsertedCustomParamsSuccess', function(){
	  it('trazendo os ultimos inseridos', function(done){
  		bm.getLastInserted(function(err, items){
				console.log(items);
				done();
  		},2, "name,slug");
	  });
	});

	describe('getLastInsertedParamsSuccess', function(){
	  it('trazendo os ultimos inseridos', function(done){
  		bm.getLastInserted(function(err, items){
				console.log(items);
				done();
  		});
	  });
	});

	describe('getTagsBySlugsSuccess', function(){
	  it('trazendo as tags ', function(done){
  		bm.getTagsBySlugs(function(err, items){
				console.log(items);
				assert(items.length, 2);
				assert(items[0].slug, "elvis");
				done();
  		}, ["elvis", "elvis-teste"]);
	  });
	});
	describe('getTagsBySlugsError', function(){
	  it('trazendo as tags ', function(done){
  		bm.getTagsBySlugs(function(err, items){
				console.log(items);
				assert(items.length, 1);
				assert.notEqual(items[0].slug, "elvis");
				done();
  		}, ["elvis-teste"]);
	  });
	});


});

