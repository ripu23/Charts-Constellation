var mainApp = angular.module("mainApp", ['ngRoute']);

mainApp.config(['$routeProvider', function($routeProvider){
  $routeProvider
  .when('/',{
    templateUrl: 'templates/home.html',
    controller: 'HomeController'
  })
  .when('/images',{
    templateUrl: 'templates/imageViewer.html',
    controller: 'ImageViewController'
  })
  .otherwise({
    redirectTo : '/'
  })
}]);
