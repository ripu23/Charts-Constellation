'use strict'
var app = angular.module("mainApp");

app.service("CoordinateService", function($http){
  this.getCoordinates = function getCoordinates(data){
    return $http({
      method: 'GET',
      url: '/coordinates/getCoordinates',
      params: {descWeight: data.descWeight, attrWeight: data.attrWeight, chartEncodingWeight: data.chartEncodingWeight, colorMap: data.colorMap}
    })
  },
  this.updateClusters = function updateClusters(data){
    return $http({
      method: 'GET',
      url: '/coordinates/updateFilter',
      params: {filter: data}
    })
  }
})
