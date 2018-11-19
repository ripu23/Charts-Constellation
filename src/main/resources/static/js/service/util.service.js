var app = angular.module("mainApp");
app.service("ClusterService", function($http) {
  this.getColors = function(num) {
    if(num > 0 && num < 66){
      return palette('mpn65', num);
    }
    return null;
  }

  this.getSequentialColors = function(num){
    if(num > 0){
      return palette('tol-dv', num);
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

  this.getImageUrl = function() {
    let images = [{
      img: 'ArunSankar1.png',
      user: 'Arun Sankar'
    },{
      img: 'ArunSankar2.png',
      user: 'Arun Sankar'
    },{
      img: 'ArunSankar3.png',
      user: 'Arun Sankar'
    }, {
      img: 'ArunSankar4.png',
      user: 'Arun Sankar'
    }, {
      img: 'ArunSankar5.png',
      user: 'Arun Sankar'
    }, {
      img: 'ArunSankar3.png',
      user: 'Arun Sankar'
    }, {
      img: 'PSoni1.png',
      user: 'P Soni'
    }, {
      img: 'Randi1.png',
      user: 'Randi'
    }, {
      img: 'Randi2.png',
      user: 'Randi'
    }, {
      img: 'Randi3.png',
      user: 'Randi'
    }, {
      img: 'Randi4.png',
      user: 'Randi'
    }, {
      img: 'Saduman1.png',
      user: 'Saduman'
    }, {
      img: 'Saduman2.png',
      user: 'Saduman'
    }, {
      img: 'Varun1.png',
      user: 'Varun'
    }, {
      img: 'Varun2.png',
      user: 'Varun'
    }, {
      img: 'Varun3.png',
      user: 'Varun'
    }];

    let constantUrl = '../images/';
    return{
      images: images,
      url: constantUrl
    };
  }
});
