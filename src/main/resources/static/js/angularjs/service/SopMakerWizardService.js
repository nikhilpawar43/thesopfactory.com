app.service('SopMakerWizardService', ['$http', function($http) {
	
	this.getQuestionSet = function( pageName, departmentId) {
		
		return $http({
			method: 'GET',
			url: 'sopwizard/getQuestionSetForPagename',
			params: {	
				'pageName' : pageName,
				'departmentId' : departmentId
			}
		});
	}
	
	this.sendQuestionsWithUserInput = function( questions ) {
		
		return $http({
			method: 'POST',
			url: 'sopwizard/sendQuestionsWithUserInput',
			data: angular.toJson( questions )
		});
	}
	
	this.setDepartmentForSop = function( question ) {
		
		return $http({
			method: 'POST',
			url: 'sopwizard/setDepartmentForSop',
			data: angular.toJson( question )
		});
	}
}]);