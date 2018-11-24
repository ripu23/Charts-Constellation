'use strict'
var app = angular.module("mainApp");

app.service("ChartService", function($http, ShareData){
  this.getChartTypes = function getChartTypes(){
    return $http({
      method: 'GET',
      url: '/chartType/getAllChartTypes',
      params: {datasetId: ShareData.data.dataSetId}
    })
  }

  this.getAllAttributes = function getAllAttributes(){
    return $http({
      method: 'GET',
      url: '/chartType/getAllAttributes',
      params: {datasetId: ShareData.data.dataSetId}
    })
  }

  this.getImages =  function getAllImages () {
    return $http({
      method: 'GET',
      url: '/chartType/getAllImages',
      params: {datasetId: ShareData.data.dataSetId}
    })
  }
})
