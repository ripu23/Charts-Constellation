'use strict'

var app = angular.module("mainApp");

app.controller("HomeController", ['$scope',
  'DistanceService',
  'CoordinateService',
  'ShareData',
  'UserService',
  function($scope, DistanceService, CoordinateService, ShareData) {

    console.log('%cReached home contrlloler', 'color :red');
    let distances = {};
    let coordinates = {};
    /* DistanceService.getAllDistances().then(function(data) {
    distances = data;
    ShareData.distances = data;
    alertify.success('Successfully imported the data.');
  }, function(err) {
    alertify.error('Something is wrong with the API --> DistanceService --> getAllDistances')
    if (err) throw err;
  });
  CoordinateService.getCoordinates().then(function(data) {
    coordinates = data;
    ShareData.coordinates = data;
    alertify.success('Successfully imported the data.');
  }, function(err) {
    alertify.error('Something is wrong with the API --> DistanceService --> getAllDistances')
    if (err) throw err;
  });

  UserService.getUsers().then(function(data){
    alertify.success('Successfully imported users');
    ShareData.users = data;
  }, function(err){
    alertify.error('Something is wrong with API --> UserService --> getUsers')
  })
  */


  }
]);
