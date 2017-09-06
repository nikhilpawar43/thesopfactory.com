app.service('SopPreviewSerice', ['$http', function( $http ) {
	
	this.loadPreview = function( questions ) {
		
		return $http({ 
			url: "sopwizard/loadSopPreviewPdf", 
			method: "GET", 
			headers: { 'Accept': 'application/pdf' }, 
			responseType: 'arraybuffer'
		});
	}
}]);