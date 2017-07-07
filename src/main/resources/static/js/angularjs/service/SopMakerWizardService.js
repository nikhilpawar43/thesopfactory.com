app.service('SopMakerWizardService', ['$http', function($http) {
	
	this.getQuestionSet = function() {
		
		return $http({
			method: 'GET',
			url: 'getQuestionSetForPage'
		});
		
	}
	
	this.getQuestionSetWithUserInput = function(sopWizardQuestionSet) {
		
		return $http({
			method: 'POST',
			url: 'getQuestionSetWithUserInput',
			data: angular.toJson( sopWizardQuestionSet )
		});
		
	}
}]);