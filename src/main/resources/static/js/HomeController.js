'use strict'

var app = angular.module("mainApp");

app.controller("HomeController", ['$scope',
  'DistanceService',
  'CoordinateService',
  'ShareData',
  'UserService',
  'ChartService',
  'ClusterService',
  function($scope, DistanceService, CoordinateService, ShareData, UserService, ChartService, ClusterService) {

    console.log('%cReached home contrlloler', 'color :red');
    let clusters = [];
    let clustersUI = {};
    let paths = [];
    let colors = [];
    const distances = ShareData.distances;
    const coordinates = ShareData.coordinates;
    const users = ShareData.users;
    const offSet = $("#charts").offset();
    $scope.users = ShareData.users;
    $scope.chartTypes = ShareData.chartTypes;
    $scope.dataCoverageCoefficient = ShareData.dataCoverageCoefficient;
    $scope.encodingCoefficient = ShareData.encodingCoefficient;
    $scope.descriptionCoefficient = ShareData.descriptionCoefficient;
    $scope.timelineLeft;
    $scope.timelineRight;
    var bubbles = new BubbleSet();
    var rectanglesA = [];
    var rectanglesB = [];
    var main = document.getElementById("main");
    var items = appendSVG(main, "g");
    var debug = appendSVG(main, "g");
    bubbles.debug(false);
    let ready = false;
    let domCreated;

    // //Get all coordinates
    //     CoordinateService.getCoordinates({
    //       "descWeight": 1.0,
    //       "attrWeight": 1.0,
    //       "chartEncodingWeight": 1.0
    //     }).then(function(data) {
    //       clusters = data.data;
    //       ShareData.clusters = data.data;
    //       createClusters(clusters);
    //       alertify.success('Successfully imported the data.');
    //       ready = true;
    //     }, function(err) {
    //       alertify.error('Something is wrong with the API --> CoordinateService --> getCoordinates')
    //       if (err) throw err;
    //     });

    ChartService.getChartTypes().then(function(data) {
      alertify.success('Successfully imported chartTypes');
      $scope.chartTypes = data.data.chartTypes;
      ShareData.chartTypes = data.data.chartTypes;
    }, function(err) {
      alertify.error('Something is wrong with API --> ChartService --> getCharts');
      if (err) throw err;
    })

    UserService.getUserCharts().then(function(data) {
      if (data && data.data) {
        ShareData.userCharts = data.data;
        $scope.users = data.data;
        colors = ClusterService.getColors(data.data.length);
        populateColorsForUsers();
      }
    }, function(err) {
      alertify.error('Something is wrong with API --> UserService --> getUsers');
      if (err) throw err;
    });

    $(document).on('moved.zf.slider', function() {
      console.log($('#timelineLeft').attr('value'));
      console.log($('#timelineRight').attr('value'));


      if(!ready || domCreated){
        var updatedAttrWeight = $('#attrWeight').attr('aria-valuenow');
        var updatedDescWeight = $('#descWeight').attr('aria-valuenow');
        var updatedChartEncodingWeight = $('#chartEncodingWeight').attr('aria-valuenow');
        var colorMap = populateColorMap();
        var dataSend = {
          "descWeight": parseFloat(updatedDescWeight),
          "attrWeight": parseFloat(updatedAttrWeight),
          "chartEncodingWeight": parseFloat(updatedChartEncodingWeight),
          "colorMap" : JSON.stringify(colorMap)
        }
        if (updatedAttrWeight && updatedDescWeight && updatedChartEncodingWeight) {
          CoordinateService.getCoordinates(dataSend).then(function(data) {
            removeAllChilds(items);
            clusters = data.data;
            ShareData.clusters = data.data;
            createClusters(clusters);
          }, function(err) {
            if (err) throw err;
          });
        }
        ready = true;
      }
    });

    function populateColorMap(){
      var colorMap = [];
      if($scope.users){
        _.forEach($scope.users, function(user){
          var toBePushed = {
            userName: user.userName,
            color: user.color
          }
          colorMap.push(toBePushed)
        });
      }
      return colorMap;
    }

    function populateColorsForUsers() {
      _.forEach($scope.users, function(user, idx) {
        $scope.users[idx].color = colors[idx];
      })
    }

    function createClusters(clustersArray) {
      clustersUI = {};
      _.forEach(clustersArray, function(clusters, i) {
        clustersUI[i] = [];
        _.forEach(clusters, function(cluster) {
          addRect(clustersUI[i], "grey", cluster.point.x , cluster.point.y, cluster.color);
        })
      });
      createPaths();
    }
    //Creation of paths and append to SVG;
    function createPaths() {
      //      paths = [];
      _.forEach(clustersUI, function(cluster, idx) {
        paths.push({
          svg: appendSVG(main, "path")
        })
      });
      update();
    }

    //Updates the padding of each cluster
    function update() {
      _.forEach(clustersUI, function(cluster1, idx1) {
        _.forEach(clustersUI, function(cluster2, idx2) {
          if (cluster1 != cluster2) {
            updateOutline(cluster1, cluster2, "grey", paths[idx1].svg);
            updateOutline(cluster2, cluster1, "grey", paths[idx2].svg);
          }
        })
      })
      domCreated = true;
    }


    //Uncomment this when everything is ready;
    // _.forEach(users, function(user){
    //   user.color = colors[user.id];
    // });

    $scope.clearFilter = function() {
      $scope.descWeight = 1;
      $scope.attrWeight = 1;
      $scope.chartEncodingWeight = 1;
      CoordinateService.getCoordinates({
        "descWeight": $scope.descWeight,
        "attrWeight": $scope.attrWeight,
        "chartEncodingWeight": $scope.chartEncodingWeight
      }).then(function(data) {
        clusters = data.data;
        ShareData.clusters = data.data;
        createClusters(clusters);
      }, function(err) {
        if (err) throw err;
      });
    }

    $scope.filterChanged = function() {
      console.log("here");
    }





    $scope.updateFilter = function() {
      console.log("reached update");
      CoordinateService.getCoordinates({
        "descWeight": $scope.descWeight,
        "attrWeight": $scope.attrWeight,
        "chartEncodingWeight": $scope.chartEncodingWeight
      }).then(function(data) {
        clusters = data;
        ShareData.clusters = data;
        createClusters(clusters);
      }, function(err) {
        if (err) throw err;
      });

    }

    function attr(elem, attr) {
      _.forEach(attr, function(val, key) {
        var value = attr[key];
        if (value === null) {
          elem.removeAttribute(key);
        } else {
          elem.setAttribute(key, value);
        }
      });
    }


    function style(elem, style) {
      _.forEach(attr, function(val, key) {
        var value = style[key];
        if (value === null) {
          delete elem.style.removeProperty(key);
        } else {
          elem.style.setProperty(key, value);
        }
      });
    }



    function removeAllChilds(parent) {
      while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
      }
    }


    function updateOutline(rectangles, otherRectangles, color, path) {
      var pad = 1;
      var list = bubbles.createOutline(
        BubbleSet.addPadding(rectangles, pad),
        BubbleSet.addPadding(otherRectangles, pad),
        null /* lines */
      );
      // rectangles need to have the form { x: 0, y: 0, width: 0, height: 0 }
      // lines need to have the form { x1: 0, x2: 0, y1: 0, y2: 0 }
      // lines can be null to infer lines between rectangles automatically
      var outline = new PointPath(list).transform([
        new ShapeSimplifier(0.0),
        new BSplineShapeGenerator(),
        new ShapeSimplifier(0.0),
      ]);
      // outline is a path that can be used for the attribute d of a path element
      attr(path, {
        "d": outline,
        "opacity": 0.5,
        "fill": color,
        "stroke": "black",
      });
      if (bubbles.debug() && path === debugFor) {
        removeAllChilds(debug);
        bubbles.debugPotentialArea().forEach(function(r) {
          var rect = appendSVG(debug, "rect");
          attr(rect, {
            x: r.x,
            y: r.y,
            width: r.width,
            height: r.height
          });
          var color = r.value === r.threshold ? "0, 0, 0" : r.value > 0 ? "150, 20, 20" : "20, 20, 150";
          style(rect, {
            "fill": "rgb(" + color + ")",
            "opacity": r.value === r.threshold ? 0.5 : Math.min(255, Math.abs(r.value * 40)) / 255.0
          });
        });
      }
    };

    function appendSVG(parent, name) {
      return parent.appendChild(document.createElementNS("http://www.w3.org/2000/svg", name));
    }

    function addRect(clustersUI, clusterColor, cx, cy, pointColor) {
      var width = 40;
      var height = 30;
      var elem = appendSVG(items, "circle"); //creates a circle
      attr(elem, {
        cx: cx,
        cy: cy,
        r: 8,
        fill: "#" + pointColor
      });
      style(elem, {
        "stroke": "black",
        "stroke-width": 1,
        "fill": clusterColor,
      });
      clustersUI.push({
        x: cx,
        y: cy,
        elem: elem,
        width: width,
        height: height
      });
    }


  }
]);
