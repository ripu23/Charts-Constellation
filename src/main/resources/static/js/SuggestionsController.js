'use strict'

var app = angular.module("mainApp");

app.controller("SuggestionsController", function($scope){
  $scope.cancel = function() {
      $mdDialog.cancel();
    };
});
