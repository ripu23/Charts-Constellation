'use strict'

var app = angular.module("mainApp");

app.controller("SuggestionsController", function($scope, $mdDialog, ShareData) {
  $scope.suggestions = ShareData.data.suggestions;
  $scope.cancel = function() {
    $mdDialog.cancel();
  };
});
