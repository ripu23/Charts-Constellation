var app = angular.module("mainApp");
app.service("ClusterService", function($http) {
  this.getColors = function(num) {
    if(num > 0 && num < 66){
      return palette('mpn65', num);
    }
    return null;
  }

  this.updateCluster = function(distances){
    let clusters = [];
    const threshold = 0.3;
    _.forEach(distances, function(val1, key1){
      _.forEach(val1, function(val2, key2){

      })
    })
    return clusters;
  }
  this.getClusters = function(){
    return $http.get("/Cluster/getClusters").then(function(response){
      return response.data;
    });
  }
});
