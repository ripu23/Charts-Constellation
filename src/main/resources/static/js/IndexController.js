'use strict'
var app = angular.module("mainApp");

app.controller("IndexController", ['$scope',
  'ShareData',
  function($scope, ShareData, ClusterService) {
    console.log('Reached index controller');
  }
]);
