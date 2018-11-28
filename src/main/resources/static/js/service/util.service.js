var app = angular.module("mainApp");
app.service("ClusterService", function($http, ShareData) {
  this.getColors = function(num) {
    if (num > 0 && num < 66) {
      return palette('mpn65', num);
    }
    return null;
  }

  this.getSequentialColors = function(num) {
    if (num > 0) {
      return palette('tol-dv', num);
    }
    return null;
  }

  this.updateCluster = function(distances) {
    let clusters = [];
    const threshold = 0.3;
    _.forEach(distances, function(val1, key1) {
      _.forEach(val1, function(val2, key2) {

      })
    })
    return clusters;
  }
  this.getClusters = function() {
    return $http.get("/Cluster/getClusters").then(function(response) {
      return response.data;
    });
  }

  this.createMapForTooltips = function(data) {
    let allDetails = [];
    _.forEach(data, function(eachCluster) {
      _.forEach(eachCluster, function(eachUser) {
        if (eachUser && eachUser.chartName) {
          allDetails[eachUser.chartName] = eachUser;
        }
      })
    })
    return allDetails;
  }

  this.getFullDatasetName = function(id){
    switch(id){
      case "olympics": return "120 years of Olympic History";
      case "crimes": return "Crimes in Chicago";
    }
  }

  this.makeTemplateForTooltip = function(id, allDetails) {
    let imageTemplate = "<img src='../images/" + allDetails[id].chartName + ".png'  />";
    let title = "<h5 class='subheader'>" + allDetails[id].title + " </h5>";
    let createdBy = "<p class= 'font-used'><strong>Created by: </strong>" + allDetails[id].userName + "</p>";
    let chartId = "<p class= 'font-used'><strong>Chart id: </strong>" + allDetails[id].chartName + " </p>";
    let chartType = "<p class= 'font-used'><strong>Chart type: </strong>" + allDetails[id].chartType + "</p>";
    let attributes = "<p class= 'font-used'><strong>Attributes: </strong>" + allDetails[id].attributes + "</p>";
    let date = "<p class= 'font-used'><strong>Creation time: </strong>" + allDetails[id].date + "</p>";
    let description = "<p class= 'font-used'><strong>Description </strong>" + allDetails[id].description + "</p>";
    let divStart = "<div>";
    let divEnd = "</div>";
    let finalTemplate = divStart + title + description + createdBy + chartId + chartType + attributes + date +
      imageTemplate + divEnd;
    return finalTemplate;

  }

  this.getImageUrl = function() {
    let images = [{
      img: 'ArunSankar1.png',
      user: 'Arun Sankar'
    }, {
      img: 'ArunSankar2.png',
      user: 'Arun Sankar'
    }, {
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

    let imagesCrimes = [{
      img: 'Anushka1.png',
      user: 'Anushka'
    }, {
      img: 'Anushka2.png',
      user: 'Anushka'
    }, {
      img: 'Anushka3.png',
      user: 'Anushka'
    }, {
      img: 'Anushka4.png',
      user: 'Anushka'
    }, {
      img: 'Anushka5.png',
      user: 'Anushka'
    }, {
      img: 'Deepika1.png',
      user: 'Deepika'
    }, {
      img: 'Deepika2.png',
      user: 'Deepika'
    }, {
      img: 'Deepika3.png',
      user: 'Deepika'
    }, {
      img: 'Deepika4.png',
      user: 'Deepika'
    }, {
      img: 'Deepika5.png',
      user: 'Deepika'
    }, {
      img: 'Katrina1.png',
      user: 'Katrina'
    }, {
      img: 'Katrina2.png',
      user: 'Katrina'
    }, {
      img: 'Katrina3.png',
      user: 'Katrina'
    }, {
      img: 'Katrina4.png',
      user: 'Katrina'
    }, {
      img: 'Katrina5.png',
      user: 'Katrina'
    }, {
      img: 'Priyanka1.png',
      user: 'Priyanka'
    }, , {
      img: 'Priyanka2.png',
      user: 'Priyanka'
    }, {
      img: 'Priyanka3.png',
      user: 'Priyanka'
    }, {
      img: 'Priyanka4.png',
      user: 'Priyanka'
    }, {
      img: 'Priyanka5.png',
      user: 'Priyanka'
    }];

    let constantUrl = '../images/';
    let tooltipUrl = "<img ng-src='" + constantUrl;
    if(ShareData.data.dataSetId == "crimes"){
      images = imagesCrimes;
    }
    return {
      images: images,
      url: constantUrl,
      tooltipUrl: tooltipUrl
    };
  }
});
