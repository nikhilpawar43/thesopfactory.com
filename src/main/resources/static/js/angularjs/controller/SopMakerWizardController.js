app.controller('SopMakerWizardController', ['SopMakerWizardService', '$scope', '$rootScope', '$window', function(SopMakerWizardService, $scope, $rootScope, $window) {
	
	$scope.pages = [	'Department', 'Overview', 'Educational', 'Aspirations'	];
	$scope.pageName = 'Department';
	$scope.departmentId = 0;
	$scope.pageTabIconCssArray = [	'', '', '', ''	];
	$scope.showOptions = false;
	var INPUT_REPLACE_VARIABLE = '${value}';
	var backupQuestionUserInput = [];
	
	$scope.tabNumber = 0;
	                                                                                                                                         
	$scope.setPageTabIconCssClass = function( position ) {
		
		for ( var i=0; i<$scope.pageTabIconCssArray.length; i++ )
			$scope.pageTabIconCssArray[i] = 'page';
		$scope.pageTabIconCssArray[position] = 'pageActive'
	}
	
	$scope.getQuestionsForPageAndDepartment = function( departmentId ) {
		
		$scope.showOptions = false;
		$scope.setPageTabIconCssClass( $scope.tabNumber );
		$scope.pageName = $scope.pages[ $scope.tabNumber ];
		$scope.questions = [];
		backupQuestionUserInput = [];
		
		SopMakerWizardService.getQuestionSet( $scope.pageName, departmentId ).then(function successCallback(response) {
			
			if ( response.data != null || response.data != undefined ) {
				$scope.questions = response.data;
				
				if ( $scope.tabNumber == 0 ) {
					if ( $scope.questions[0] != null && $scope.questions[0].userOption != 0 )
						$scope.questions[0].userOption = {	id: $scope.questions[0].userOption	};
				} else if ( $scope.tabNumber > 0 ) {
					if ( $scope.questions != null && $scope.questions.length > 1 ) {
						for ( var i=1; i<$scope.questions.length; i++ ) {
							if ( $scope.questions[i].userOption != 0 ) {
								$scope.showOptions = true;
								break;
							}
						}
					}
					
					for ( var i=0; i<$scope.questions.length; i++ ) {
						if ( $scope.questions[i].userInput != null ) {
							backupQuestionUserInput.push( $scope.questions[i].userInput );
						}
					}
					
				}
				
			} else {
				console.log('There was an error while processing the questions JSON !');
			}
		}, function errorCallback( response ) {
			console.log('There was an error while fetching SOP wizard question set: ' + response.data.length);
		});
		
	}
	
	/*********************************************************************************************************************
	 ********************************** 	THE MAIN PROGRAM EXECUTION BEGINS HERE		*********************************
	*********************************************************************************************************************/
	
	if ( $rootScope.departmentId != null && $rootScope.departmentId != undefined) {
		$scope.departmentId = $rootScope.departmentId;
		$scope.tabNumber = $rootScope.tabNumber;
	}
	$scope.getQuestionsForPageAndDepartment( $scope.departmentId );
	
	/*********************************************************************************************************************
	 *************************************** 	END OF MAIN PROGRAM EXECUTUION		*************************************
	*********************************************************************************************************************/
	
	$scope.getQuestionsForTabAndDepartment = function( departmentId, tabNumber ) {
		
		if ( departmentId == 0 && tabNumber > 0 ) {
			alert('Please select a department before navigating to other tabs !');
		} else if ( departmentId != 0 && tabNumber >= 0 ) {
			$scope.tabNumber = tabNumber;
			$scope.getQuestionsForPageAndDepartment( $scope.departmentId );
		}
	}
	
	$scope.submitAnswers = function() {
		
		if ( $scope.tabNumber == 0 ) {

			SopMakerWizardService.setDepartmentForSop( $scope.questions[0] ).then( function successCallback() {
				
				$scope.showOptions = false;
				$scope.departmentId = $scope.questions[0].userOption;
				$scope.tabNumber ++;
				$scope.getQuestionsForPageAndDepartment( $scope.departmentId );
				
			}, function errorCallback() {
				
			});
		} else if ( $scope.tabNumber > 0 ) {
			
			$scope.showOptions = true;
			for ( var i=0; i<$scope.questions.length; i++ ) {
				
				for ( var j=0; j<$scope.questions[i].options.length; j++ ) {
					
					if ( $scope.questions[i].options[j].value.includes( INPUT_REPLACE_VARIABLE ) ) {
						$scope.questions[i].options[j].value = $scope.questions[i].options[j].value.replace( INPUT_REPLACE_VARIABLE, $scope.questions[i].userInput );
					} else if ( ($scope.questions[i].options[j].value.indexOf( backupQuestionUserInput[i] ) != -1) && ( $scope.questions[i].userInput != backupQuestionUserInput[i]) ) {
						$scope.questions[i].options[j].value = $scope.questions[i].options[j].value.replace( backupQuestionUserInput[i], $scope.questions[i].userInput );
					}
				}
			}
			
			backupQuestionUserInput = [];
			for ( var i=0; i<$scope.questions.length; i++ ) {
				if ( $scope.questions[i].userInput != null ) {
					backupQuestionUserInput.push( $scope.questions[i].userInput );
				}
			}
			
		}
	}
	
	$scope.saveOptions = function() {

		SopMakerWizardService.sendQuestionsWithUserInput( $scope.questions ).then( function successCallback( response ){
			console.log('The sop details updated successfully on SOP server.');
		}, function errorCallback() {
			
		});
		
		$scope.tabNumber ++;
		$scope.getQuestionsForPageAndDepartment( $scope.departmentId );
	}
	
	$scope.loadPDFPreview = function() {
		$rootScope.tabNumber = $scope.tabNumber;
		$rootScope.departmentId = $scope.departmentId;
		$window.location.href = '#/sopmaker/sopmaker-preview';
	}
	
}]);