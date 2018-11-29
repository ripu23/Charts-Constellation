'use strict'

var app = angular.module("mainApp");

app.controller("SuggestionsController", function($scope, $mdDialog){
  $scope.cancel = function() {
      $mdDialog.cancel();
    };
});
