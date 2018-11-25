'use strict'

var app = angular.module("mainApp");
app.controller("ImageViewController", ['$scope',
 'ClusterService',
 function($scope, ClusterService) {
  console.log('reached ImageViewController');
  $scope.images = ClusterService.getImageUrl().images;
  $scope.constantUrl = ClusterService.getImageUrl().url;
  $scope.$on('datasetChanged', function(event, data){
    $scope.images = ClusterService.getImageUrl().images;
    $scope.constantUrl = ClusterService.getImageUrl().url;
  });
}]);
