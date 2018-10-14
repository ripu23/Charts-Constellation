var homeApp = angular.module("mainApp", ["ngRoute"]);

homeApp.config(['$routeProvider', function($routeProvider){
	$routeProvider
	
	.when('/checkout',{
		templateUrl: '../templates/checkout.html',
		controller : 'CheckoutController'
	})
	.when('/summary',{
		templateUrl: '../templates/summary.html',
		controller : 'CheckoutController'
	})
	.when('/',{
		templateUrl: '../templates/movieList.html',
		controller : 'HomeController'
	})
	.otherwise({
		redirectTo: '/'
	})
}]);