app.config( function($stateProvider, $urlRouterProvider) {
	
	$urlRouterProvider.otherwise("/home");
	
	$stateProvider.state('home', {
		url: '/home',
		views: {
			'mainContent': {
				templateUrl: 'partials/home.html',
				controller: 'HomeController'
			}
		}
	}).state('sopmaker', {
		url: '/sopmaker',
		views: {
			'mainContent': {
				templateUrl: 'partials/sopmaker.html',
				controller: 'SopmakerController',
			}
		}
	}).state('sopmaker.overview', {
		url: '/sopmaker-overview',
		views: {
			'sopMakerWizard': {
				templateUrl: 'partials/sopmaker-overview.html',
				controller: 'SopMakerWizardController'
			},
			'sopMakerWizardOptions': {
				templateUrl: 'partials/sopmaker-overview-options.html',
				controller: 'SopMakerWizardOverviewController'
			}
		}
		
	});
	
});