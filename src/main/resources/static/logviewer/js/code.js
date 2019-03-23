var mg = {
	defaults: {
		cache: false
	}
}

var myapp = angular.module('myapp', []);
myapp.controller('menuController', function ($scope,$http) {
	$scope.log = [];
	var logs = {};
	$scope.verLogs = function(){
		$scope.log = logs[this.data];
		console.debug($scope.log);
	}
	$http.get("config.js").success(function(config){
		$.fn.extend(mg.defaults, config);
		$http.get(mg.defaults.logUrl,{
			cache: mg.defaults.cache,
			headers:{
				"Access-Control-Allow-Origin": ""
			},
	    transformResponse: function (rawLog, headersGetter) {
	    	// {"level":"info","message":"bookmark atualizado","timestamp":"2015-11-14 17:15:03:481"}
	    	var lines = rawLog.split('\n'),
	    			datas = {};
	      lines.map(function(line){
	      	try{
		      	var log = JSON.parse(line);
		      	var key = log.timestamp.split(' ')[0];
		      	datas[key] = '\0';
		      	if(!logs[key]){
		      		logs[key] = [];
		      	}
		    		logs[key].push(log);
	    		}catch(e){
	      		console.debug("erro ao passar", line, e);
	    		}
	      });
	      return Object.keys(datas).reverse();
	    }
		}).then(function(response) {
			console.log("baixou", response);
			$scope.datas = response.data; 
		}, function(err){
			console.debug("err", err);
		});
	});
});