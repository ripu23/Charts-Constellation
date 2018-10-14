'use strict'
var app = angular.module("mainApp");
homeApp.controller("CheckoutController", [ '$scope', 'ShareData', 'CartUtils', '$rootScope','$location',function($scope, ShareData, CartUtils, $rootScope, $location) {
	console.log('Reached checkout controller.');
	$scope.cart = ShareData.cart;
	var discountData = CartUtils.calc($scope.cart);
	$scope.totalBillAmount = discountData.totalBillAmount;
	$scope.discountReceived = discountData.discountsReceived;
	$scope.discountAmount = discountData.discountAmount;
	$scope.discountedAmount = discountData.discountedAmount;
	$scope.removeFromCart = function(data){
		$scope.cart = CartUtils.removeFromCart($scope.cart, data.data);
		$rootScope.$broadcast('added');
		discountData = CartUtils.calc($scope.cart);
		$scope.totalBillAmount = discountData.totalBillAmount;
		$scope.discountReceived = discountData.discountsReceived;
		$scope.discountAmount = discountData.discountAmount;
		$scope.discountedAmount = discountData.discountedAmount;
	}
	$scope.addToCart = function(data){
		$scope.cart = CartUtils.addToCart($scope.cart, data.data);
		$rootScope.$broadcast('added');
		discountData = CartUtils.calc($scope.cart);
		$scope.totalBillAmount = discountData.totalBillAmount;
		$scope.discountReceived = discountData.discountsReceived;
		$scope.discountAmount = discountData.discountAmount;
		$scope.discountedAmount = discountData.discountedAmount;
	}
	$scope.order = function() {
		$location.path("/summary");
	}
	$scope.reset = function () {
		ShareData.cart = [];
		$rootScope.$broadcast('added');
		$location.path("/");
	}
			
} ]);