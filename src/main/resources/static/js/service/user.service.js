'use strict'
var app = angular.module("mainApp");

app.service("UserService", function($http){
  this.getUserCharts = function getUserCharts(){
    return $http({
      method: 'GET',
      url: '/users/getUserCharts'
    })
  }
})
