app.controller('LoginController', ['$scope', '$rootScope', '$http', '$location', 'LoginService', function( $scope, $rootScope, $http, $location, LoginService ) {
	
	console.log('Executing the login controller');
	
	$scope.authenticate = function( callback ) {
		
		/*$http.get('users/user').success(function(data) {
		      if (data.name) {
		        $rootScope.authenticated = true;
		      } else {
		        $rootScope.authenticated = false;
		      }
		      callback && callback();
		    }).error(function() {
		      $rootScope.authenticated = false;
		      callback && callback();
		    });*/
		
		
		var headers = credentials ? {	authorization: 'Basic ' + btoa( credentials.username + ':' + credentials.password )	  } : {};
		
	}
	
	$scope.credentials = {};
	$scope.error = false;
//	$scope.authenticate();
	
	$scope.login = function() {
		
		var headers = {    "content-type" : "application/x-www-form-urlencoded"	};
		
		LoginService.login( headers, $scope.credentials ).then( function successCallback( response ) {
			
			if ( response.data != null && response.data.id != null ) {
				console.log('The user: ' + response.data.username + ' authenticated successfully.');
				$rootScope.authenticated = true;
				$scope.error = false;
				$location.path("/#/home/");
			} else {
				console.log('The user authentication failed.');
				$rootScope.authenticated = false;
				$scope.error = true;
				$location.path("/#/home/login.html");
			}
		}, function errorCallback() {
			$rootScope.authenticated = false;
			$scope.error = true;
		});
		
		/*$http.post('loginCheck', $.param($scope.credentials), {
		      headers : {
		        "content-type" : "application/x-www-form-urlencoded"
		      }
		    }).success(function(data) {
		      authenticate(function() {
		        if ($rootScope.authenticated) {
		          $location.path("/");
		          $scope.error = false;
		        } else {
		          $location.path("/home/login.html");
		          $scope.error = true;
		        }
		      });
		    }).error(function(data) {
		      $location.path("/home/login.html");
		      $scope.error = true;
		      $rootScope.authenticated = false;
		    })*/
		
		/*$scope.authenticate( $scope.credentials );
		if ( $rootScope.authenticated ) {
			$location.path( '/sopmaker' );
			$scope.error = false;
		} else {
			$location.path( '/login' );
			$scope.error = true;
		}*/
	}
	
}]);