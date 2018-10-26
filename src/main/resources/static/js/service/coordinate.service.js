'use strict'
var app = angular.module("mainApp");

app.factory("CoordinateService", function($http){
  return{
    getCoordinates: function(){
      return $http.get("/coordinate/getCoordinates", function(response){
        return response.data;
      })
    }
  }
})
