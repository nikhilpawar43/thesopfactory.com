app.config( function( $stateProvider, $locationProvider, $urlRouterProvider ) {
	
	$stateProvider.state('home', {
		url: '/home',
		views: {
			'mainContent': {
				templateUrl: 'partials/content.html',
			}
		}
	}).state('home.info', {
		url: '/info.html',
		views: {
			'sopWizard': {
				templateUrl: 'partials/info.html'
			}
		}
	}).state('home.createsop', {
		url: '/sopmaker-create.html',
		views: {
			'sopWizard': {
				templateUrl: 'partials/sopmaker-wizard.html',
				controller: 'SopMakerWizardController'
			}
		}
	}).state('home.preview', {
		url: '/sopmaker-preview.html', 
		views: {
			'sopWizard': {
				templateUrl: 'partials/sop-preview.html',
				controller: 'SopPreviewController'
			}
		}
	}).state('home.login', {
		url: '/login.html',
		views: {
			'sopWizard': {
				templateUrl: 'partials/loginPage.html',
				controller: 'LoginController'
			}
		}
	}).state('home.403', {
		url: '/403.html',
		views: {
			'sopWizard': {
				templateUrl: 'partials/errors/403/403.html'
			}
		}
	});
	
	$urlRouterProvider.otherwise("/home");
	/*$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});*/
});