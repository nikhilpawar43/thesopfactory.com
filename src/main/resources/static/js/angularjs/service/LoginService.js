app.service('LoginService', ['$http', function( $http ) {
	
	this.login = function( headers, credentials ) {
		
		return $http({
			url: 'loginValidationUrl',
			method: 'POST',
			headers: headers,
			data: $.param( credentials )
 		});
	}
}]);