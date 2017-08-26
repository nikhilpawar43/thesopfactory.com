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
	}).state('sopmaker.create', {
		url: '/sopmaker-create',
		views: {
			'sopWizard': {
				templateUrl: 'partials/sopmaker-wizard.html',
				controller: 'SopMakerWizardController'
			}
		}
	}).state('sopmaker.preview', {
		url: '/sopmaker-preview', 
		views: {
			'sopWizard': {
				templateUrl: 'partials/sop-preview.html',
				controller: 'SopPreviewController'
			}
		}
	});
	
});