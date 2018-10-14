'use strict'
var app = angular.module("mainApp");

app.factory("movieService", function($http){
	return {
        getAllMovies: function() {
            return $http.get("/movies/getAll").then(function(response) {
                return response.data;
            });
        }
    }
})