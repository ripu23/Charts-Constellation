'use strict'
var app = angular.module("mainApp");

app.controller("IndexController", ['$scope',
  'ShareData',
  function($scope, ShareData, ClusterService) {
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
  }
]);
