'use strict'
var app = angular.module("mainApp");

app.service("UserService", function($http, ShareData){
  this.getUserCharts = function getUserCharts(){
    return $http({
      method: 'GET',
      url: '/users/getUserCharts',
      params: {datasetId: ShareData.data.dataSetId}
    })
  }
})
