
describe('User', function() {
  describe('#save()', function() {
    it('should save without error', function(done) {
      var user = new User('Luna');
      user.save(function(err) {
        if (err) throw err;
        done();
      });
    });
  });
});

User = function(){
	console.log("novo usuario", arguments);
	this.save = function(callback){
		console.log("salvando", arguments);
		callback();
	}
};
