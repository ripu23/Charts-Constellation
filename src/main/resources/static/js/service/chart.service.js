'use strict'
var app = angular.module("mainApp");

app.service("ChartService", function($http){
  this.getChartTypes = function getChartTypes(){
    return $http({
      method: 'GET',
      url: '/chartType/getAllChartTypes'
    })
  }
})
