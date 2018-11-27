'use strict'
var app = angular.module("mainApp");

app.controller("IndexController", ['$scope',
  'ShareData',
  '$rootScope',
  '$location',
  function($scope, ShareData, $rootScope, $location) {
    console.log('Reached index controller');

    $scope.switchDataset = function(){
      if(ShareData.data.dataSetId.includes("olympics")){
        ShareData.data.dataSetId = "crimes";
        $scope.$broadcast('datasetChanged');
      }else{
        ShareData.data.dataSetId = "olympics";
        $scope.$broadcast('datasetChanged');
      }
    }

    $scope.goToHome = function(){
      $rootScope.$broadcast('routeToHome');
      $location.path('/');
    }
  }
]);
