'use strict'
var app = angular.module("mainApp");
homeApp.controller("HomeController", [
		'$scope',
		'movieService',
		'ShareData',
		'$rootScope',
		'CartUtils',
		function($scope, movieService, ShareData, $rootScope, CartUtils) {
			console.log('Reached Home controller');

			$(document).ready(function(){
				$('[tag]').tooltip({
					trigger : 'manual'
				});
			}); 
			$scope.availableMovie;
			$scope.availableMovies = movieService.getAllMovies().then(
					function(data) {
						$scope.availableMovies = data;
					});
			$scope.cart = ShareData.cart;
			$scope.addToCart = function(data) {
				$scope.cart = CartUtils.addToCart($scope.cart, data);
				$('#'+(data.id-1)+'-tag').tooltip('show');
				$rootScope.$broadcast('added');

			}
			$scope.removeFromCart = function(data) {

				$scope.cart = CartUtils.removeFromCart($scope.cart, data);
				$rootScope.$broadcast('added');
			}

			function updateCart(index, flag) {
				if (index >= 0 && $scope.cart.length > 0) {
					if (flag === 'add') {
						$scope.cart[index].length++;
					} else {
						$scope.cart[index].length--;
					}

					$scope.cart[index].total = $scope.cart[index].length
							* $scope.cart[index].data.price;
				}
				if ($scope.cart) {
					ShareData.cart = $scope.cart;
				}

			}

		} ]);