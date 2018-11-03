var app = angular.module("mainApp");
app.service("ClusterService", function() {
  this.getColors = function(num) {
    if(num > 0 && num < 66){
      return palette('mpn65', num);
    }
    return null;
  }

  this.createClusters = function(distances){
    let clusters = [];
    const threshold = 0.3;
    _.forEach(distances, function(val1, key1){
      _.forEach(val1, function(val2, key2){

      })
    })
    return clusters;
  }
});
