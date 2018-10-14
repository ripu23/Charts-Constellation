'use strict'
var app = angular.module("mainApp");

app.controller("PirateController",['$scope', 'ShareData','$rootScope', function($scope, ShareData, $rootScope){
	
	$rootScope.$on('added', function(event) {
		var cart = ShareData.cart;
		$scope.cartLength = 0;
		_.forEach(cart, function(elem){
			$scope.cartLength += elem.length;
		})
		}
	);
	
}]);