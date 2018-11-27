'use strict'

var app = angular.module("mainApp");
app.controller("ImageViewController", ['$scope',
 'ClusterService',
 'ShareData',
 function($scope, ClusterService, ShareData) {
  console.log('reached ImageViewController');
  $scope.images = ClusterService.getImageUrl().images;
  $scope.constantUrl = ClusterService.getImageUrl().url;
  let allDetails = ShareData.data.allDetails;
  function makeMap(){
    _.forEach($scope.images, function(image, key){
      _.forEach(allDetails[image.img.substring(0,image.img.indexOf('.'))], function(data, idx){
          image[idx] = data;
      });
    });
  }
  $scope.$on('datasetChanged', function(event, data){
    $scope.images = ClusterService.getImageUrl().images;
    $scope.constantUrl = ClusterService.getImageUrl().url;
  });
  makeMap();
}]);
