app.config( function($stateProvider, $urlRouterProvider) {
	
	/*	route to show our basic form (/form)	*/	
	$stateProvider.state('sopmaker', {
		url: '/sopmaker',
        templateUrl: 'sopmaker.html',
        controller: 'SopmakerController'
	}).state('sopmaker.overview', {
        url: '/sop-overview',
        templateUrl: 'sopmaker-overview.html'
    }).state('sopmaker.educational', {
        url: '/sop-educational',
        templateUrl: 'sopmaker-educational.html'
    }).state('sopmaker.aspirations', {
        url: '/sop-aspirations',
        templateUrl: 'sopmaker-aspirations.html'
    });
	
	$urlRouterProvider.otherwise('/form/profile');
});
