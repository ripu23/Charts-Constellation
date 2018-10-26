'use strict'
var app = angular.module("mainApp");

app.factory("DistanceService", function($http){
  return{
    getAllDistances: function(){
      return $http.get("/distance/getAllDistances").then(function(response){
        return response.data;
      });
    }
  }
})
