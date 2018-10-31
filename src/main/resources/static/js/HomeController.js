'use strict'

var app = angular.module("mainApp");

app.controller("HomeController", ['$scope', 'DistanceService', 'CoordinateService', function($scope, DistanceService, CoordinateService) {

console.log('%cReached home contrlloler', 'color :red');
  let distances = {};
  let coordinates = {};
  /* DistanceService.getAllDistances().then(function(data) {
    distances = data;
    alertify.success('Successfully imported the data.');
  }, function(err) {
    alertify.error('Something is wrong with the API --> DistanceService --> getAllDistances')
    if (err) throw err;
  });
  CoordinateService.getCoordinates().then(function(data) {
    coordinates = data;
    alertify.success('Successfully imported the data.');
  }, function(err) {
    alertify.error('Something is wrong with the API --> DistanceService --> getAllDistances')
    if (err) throw err;
  });

  */
  

}]);
