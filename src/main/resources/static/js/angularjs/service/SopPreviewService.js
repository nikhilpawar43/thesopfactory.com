app.service('SopPreviewSerice', ['$http', function( $http ) {
	
	this.loadPreview = function( questions ) {
		
		return $http({ 
			url: "sopwizard/loadSopPreviewPdf", 
			method: "POST", 
			headers: { 'Accept': 'application/pdf' }, 
			responseType: 'arraybuffer',
			data: angular.toJson( questions )
		});
	}
}]);