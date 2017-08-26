app.service('SopMakerWizardService', ['$http', function($http) {
	
	this.getQuestionSet = function( pageName, departmentName) {
		
		return $http({
			method: 'GET',
			url: 'sopwizard/getQuestionSetForPagename',
			params: {	
				'pageName' : pageName,
				'departmentName' : departmentName
			}
		});
		
	}
	
	this.getQuestionSetWithUserInput = function( questions ) {
		
		return $http({
			method: 'POST',
			url: 'sopwizard/setQuestionSetWithUserInput',
			data: angular.toJson( questions )
		});
		
	}
}]);