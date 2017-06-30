var app = {log: function(){console.log(arguments);}};
process.app = app;
app.db = require("./../../src/core/SqliteConnector").open();
// var sqlite3 = require('sqlite3').verbose();
// var db = new sqlite3.Database(':memory:');

try { db.run("INSERT INTO bookmark (id) VALUES (1)"); }
catch (e) {
  console.log('got exception');
  console.log(e);
}

// var assert = require("assert");
// var m = require("./../../src/model/TestModel")(app);


// try{
// 	m.cadTest();
// }catch(e){
// 	console.error("erro",e);
// }
// // describe('Test', function() {
// // 	describe('getTagsBySlugsError', function(){
// // 	  it('trazendo as tags ', function(done){
// //   		m.cadTest();
// // 	  });
// // 	});


// // });

