app.controller('SopMakerWizardController', ['SopMakerWizardService', '$rootScope', '$scope', function(SopMakerWizardService, $rootScope, $scope) {
	
	/*
	1. Send a request to TheSOPMakerController.java to get the page 1 question set.
	2. Get the json array of the questions set.
	3. Display the questions on the page using ng-repeat.
	*/
	
	$rootScope.showOptions = false;

	SopMakerWizardService.getQuestionSet().then(function successCallback(response) {
		
		if ( response.data != null || response.data != undefined ) {
			
			$scope.sopWizardQuestionSet = response.data;
			
		} else {
			console.log('There was an error while fetching SOP wizard question set.');
		}
		
	}, function errorCallback() {
		console.log('There was an error while fetching SOP wizard question set.');
	});
	
	$scope.submitOverviewAnswer = function() {
		
		SopMakerWizardService.getQuestionSetWithUserInput($scope.sopWizardQuestionSet).then(function successCallback(response) {
			
			$rootScope.showOptions = true;
			$rootScope.questionSetWithUserInput = response.data.sopWizardQuestionList;
		}, function errorCallback() {
			
		});
		
	}

}]);