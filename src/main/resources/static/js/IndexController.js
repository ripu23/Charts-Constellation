'use strict'
var app = angular.module("mainApp");

app.controller("IndexController", ['$scope', function($scope) {
  console.log('Reached index controller');
  $scope.clearFilter = function() {
    console.log("reached Clear");
  }
  $scope.updateFilter = function() {
    console.log("reached update");
  }
}]);
