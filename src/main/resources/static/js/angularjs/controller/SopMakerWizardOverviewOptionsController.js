app.controller('SopMakerWizardOverviewController', ['SopMakerWizardService','$scope', function(SopMakerWizardService, $scope) {
	
	SopMakerWizardService.getQuestionSet().then(function successCallback(response) {
		
		if ( response.data != null || response.data != undefined ) {
			
			$scope.sopWizardQuestionSet = response.data.sopWizardQuestionList;
			
		} else {
			console.log('There was an error while fetching SOP wizard question set.');
		}
		
	}, function errorCallback() {
		console.log('There was an error while fetching SOP wizard question set.');
	});
}]);