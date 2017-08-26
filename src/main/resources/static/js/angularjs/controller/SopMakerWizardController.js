app.controller('SopMakerWizardController', ['SopMakerWizardService', '$scope', '$rootScope', function(SopMakerWizardService, $scope, $rootScope) {
	
	$scope.pages = [	'Department', 'Overview', 'Educational', 'Aspirations'	];
	$scope.pageName = 'Department';
	$scope.departmentName = 'Department';
	$scope.pageTabIconCssArray = [	'', '', '', ''	];
	$scope.showOptions = false;
	$scope.sop = {};
	var INPUT_REPLACE_VARIABLE = '${value}';
	
	$scope.tabNumber = 0;
	                                                                                                                                         
	$scope.setPageTabIconCssClass = function( position ) {
		
		for ( var i=0; i<$scope.pageTabIconCssArray.length; i++ )
			$scope.pageTabIconCssArray[i] = 'page';
		$scope.pageTabIconCssArray[position] = 'pageActive'
	}
	
	$scope.getQuestionsForPageAndDepartment = function( departmentName ) {
		
		$scope.showOptions = false;
		$scope.setPageTabIconCssClass( $scope.tabNumber );
		$scope.pageName = $scope.pages[ $scope.tabNumber ];
		
		var questionsFoundFlag = 0;
		$scope.questions = [];
		
		if ( $scope.sop.questions != null && $scope.sop.questions.length>0 ) {
			
			for ( var i=0; i<$scope.sop.questions.length; i++ ) {
				var question = $scope.sop.questions[i];
				
				if ( question.page == $scope.pageName ) {
					questionsFoundFlag = 1;
					$scope.questions.push( question );
				}
			}
			
			if ( questionsFoundFlag == 0 ) {
				console.log('Fetching question set from SOP server.');
				SopMakerWizardService.getQuestionSet( $scope.pageName, departmentName ).then(function successCallback(response) {
					
					if ( response.data != null || response.data != undefined ) {
						$scope.questions = response.data;
					} else {
						console.log('There was an error while processing the questions JSON !');
					}
				}, function errorCallback() {
					console.log('There was an error while fetching SOP wizard question set !');
				});
			}
		} else {
			SopMakerWizardService.getQuestionSet( $scope.pageName, departmentName ).then(function successCallback(response) {
				
				if ( response.data != null || response.data != undefined ) {
					$scope.questions = response.data;
				} else {
					console.log('There was an error while processing the questions JSON !');
				}
			}, function errorCallback() {
				console.log('There was an error while fetching SOP wizard question set !');
			});
		}
	}
	
	$scope.getQuestionsForPageAndDepartment( $scope.departmentName );
	
	$scope.getQuestionsForTabAndDepartment = function( departmentName, tabNumber ) {
		
		if ( departmentName == 'Department' && tabNumber > 0 ) {
			alert('Please select a department before navigating to other tabs !');
		} else {
			$scope.tabNumber = tabNumber;
			$scope.getQuestionsForPageAndDepartment( $scope.departmentName );
		}
	}
	
	$scope.submitAnswers = function() {
		
		console.log('The tab number is: ' + $scope.tabNumber );
		
		if ( $scope.questions != null && $scope.questions.length >= 1 ) {
			
			if ( $scope.tabNumber == 0 ) {
				
				$scope.showOptions = false;
				$scope.departmentName = $scope.questions[0].departmentName.value;
				$scope.tabNumber ++;
				
				if ( $scope.sop.questions == null )
					$scope.sop.questions = [];
				
				$scope.sop.questions.push( $scope.questions[0] );
				
				$scope.getQuestionsForPageAndDepartment( $scope.departmentName );
				
			} else if ( $scope.tabNumber > 0 ) {
				
				$scope.showOptions = true;
				for ( var i=0; i<$scope.questions.length; i++ ) {
					for ( var j=0; j<$scope.questions[i].options.length; j++ ) {
						$scope.questions[i].options[j].value = $scope.questions[i].options[j].value.replace( INPUT_REPLACE_VARIABLE, $scope.questions[i].userInput );
					}
				}
				
			} else if ( $scope.tabNumber == 3 ) {
				
			} else if ( $scope.tabNumber == 4 ) {
				
			}
		}
	}
	
	$scope.saveOptions = function() {

		console.log( 'The question is: ' + $scope.questions[0].question );
		console.log( 'The user has selected option: ' + $scope.questions[0].userOptionId.id );
		
		if ( $scope.sop.questions == null )
			$scope.sop.questions = [];
		
		for ( var i=1; i<$scope.questions.length; i++ )
			$scope.sop.questions.push( $scope.questions[i] );

		console.log( 'The number of questions in SOP are: ' + $scope.sop.questions.length );
		
		$rootScope.sop = $scope.sop;
		
		$scope.tabNumber ++;
		$scope.getQuestionsForPageAndDepartment( $scope.departmentName );
	}
	
}]);