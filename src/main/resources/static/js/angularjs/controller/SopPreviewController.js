app.controller('SopPreviewController', ['SopPreviewSerice', '$scope', '$rootScope', '$sce', '$window', function( SopPreviewSerice, $scope, $rootScope, $sce, $window ) {
	
	console.log('Loading the SOP preview');
		
	SopPreviewSerice.loadPreview().then( function successCallback( response ) {
		
		var file = new Blob([response.data], {type: 'application/pdf'});
		var fileURL = $window.URL.createObjectURL(file);
		$scope.content = $sce.trustAsResourceUrl(fileURL);
	}, function errorCallback() {
		console.log('There was an error while downloading the SOP PDF file.');
	});
	
	$scope.navigateBackToSopMaker = function() {
		$window.location.href = '#/sopmaker/sopmaker-create';
	}
	
}]);