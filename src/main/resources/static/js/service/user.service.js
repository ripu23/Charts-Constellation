'use strict'
var app = angular.module("mainApp");

app.factory("UserService", function($http){
  return{
    getUsers: function(){
      return $http.get("/Users/getUsers", function(response){
        return response.data;
      })
    }
  }
})
