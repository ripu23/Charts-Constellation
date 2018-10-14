var mainApp = angular.module("mainApp", ['ngRoute']);

mainApp.config(['$routeProvider', function($routeProvider){
  $routeProvider
  .when('/',{
    templateUrl: 'templates/home.html',
    controller: 'HomeController'
  })
  .otherwise({
    redirectTo : '/'
  })
}]);
