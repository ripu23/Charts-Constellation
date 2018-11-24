'use strict'
var app = angular.module("mainApp");

app.controller("IndexController", ['$scope',
  'ShareData',
  function($scope, ShareData, ClusterService) {
    console.log('Reached index controller');

    $scope.switchDataset = function(){
      if(ShareData.olympics){
        ShareData.olympics = !ShareData.olympics;
      }
      if(ShareData.crimes){
        ShareData.crimes = !ShareData.crimes;
      }
    }
  }
]);
