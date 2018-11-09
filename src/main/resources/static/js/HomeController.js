'use strict'

var app = angular.module("mainApp");

app.controller("HomeController", ['$scope',
  'DistanceService',
  'CoordinateService',
  'ShareData',
  'UserService',
  'ClusterService',
  function($scope, DistanceService, CoordinateService, ShareData, UserService, ClusterService) {

    console.log('%cReached home contrlloler', 'color :red');
    // let coordinates = [];
    let clusters = [];
    let clustersUI = {};
    let paths = [];
    const distances = ShareData.distances;
    const coordinates = ShareData.coordinates;
    const users = ShareData.users;
    const offSet = $("#charts").offset();
    $scope.descWeight = ShareData.descWeight;
    $scope.attrWeight = ShareData.attrWeight;
    $scope.chartEncodingWeight = ShareData.chartEncodingWeight;
    // let colors = ClusterService.getColors(users.length);
    $scope.dataCoverageCoefficient = ShareData.dataCoverageCoefficient;
    $scope.encodingCoefficient = ShareData.encodingCoefficient;
    $scope.descriptionCoefficient = ShareData.descriptionCoefficient;
    var bubbles = new BubbleSet();
    var rectanglesA = [];
    var rectanglesB = [];
    var main = document.getElementById("main");
    var items = appendSVG(main, "g");
    var pathA = appendSVG(main, "path");
    var pathB = appendSVG(main, "path");
    var debug = appendSVG(main, "g");
    bubbles.debug(false);
    var debugFor = pathA;


    //Get all distances
    // DistanceService.getAllDistances().then(function(data) {
    //   distances = data;
    //   ShareData.distances = data;
    //   alertify.success('Successfully imported the data.');
    // }, function(err) {
    //   alertify.error('Something is wrong with the API --> DistanceService --> getAllDistances')
    //   if (err) throw err;
    // });
    //
    // //Get all coordinates
    CoordinateService.getCoordinates({
      "descWeight": 1.0,
      "attrWeight": 1.0,
      "chartEncodingWeight": 1.0
    }).then(function(data) {
      clusters = data.data;
      ShareData.clusters = data.data;
      createClusters(clusters);
      alertify.success('Successfully imported the data.');
    }, function(err) {
      alertify.error('Something is wrong with the API --> CoordinateService --> getCoordinates')
      if (err) throw err;
    });
    //
    // UserService.getUsers().then(function(data) {
    //   alertify.success('Successfully imported users');
    //   ShareData.users = data;
    // }, function(err) {
    //   alertify.error('Something is wrong with API --> UserService --> getUsers')
    // })

    //Creation of clusers
    function createClusters(clustersArray) {
      _.forEach(clustersArray, function(clusters, i) {
        clustersUI[i] = [];
        _.forEach(clusters, function(cluster) {
          addRect(clustersUI[i], "red", cluster.point.x + offSet.left, cluster.point.y + offSet.top);
        })
      });
      createPaths();
    }
    //Creation of paths and append to SVG;
    function createPaths() {
      _.forEach(clustersUI, function(cluster, idx) {
        paths.push({
          svg: appendSVG(main, "path")
        })
      });
      update();
      // updateOutline(clustersUI[0], clustersUI[1], "grey", paths[0].svg);
      // updateOutline(clustersUI[1], clustersUI[0], "grey", paths[1].svg);
    }

    //Updates the padding of each cluster
    function update() {
      _.forEach(clustersUI, function(cluster1, idx1) {
        _.forEach(clustersUI, function(cluster2, idx2) {
          updateOutline(cluster1, cluster2, "crimson", paths[idx1].svg);
          updateOutline(cluster2, cluster1, "crimson", paths[idx2].svg);
        })
      })
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
    $(document).on('moved.zf.slider', function() {
      $(document).ready(function() {
        var updatedAttrWeight = $('#attrWeight').attr('aria-valuenow');
        var updatedDescWeight = $('#descWeight').attr('aria-valuenow');
        var updatedChartEncodingWeight = $('#chartEncodingWeight').attr('aria-valuenow');
        if (updatedAttrWeight && updatedDescWeight && updatedChartEncodingWeight) {
          CoordinateService.getCoordinates({
            "descWeight": parseFloat(updatedDescWeight),
            "attrWeight": parseFloat(updatedAttrWeight),
            "chartEncodingWeight": parseFloat(updatedChartEncodingWeight)
          }).then(function(data) {
            clusters = data.data;
            ShareData.clusters = data.data;
            createClusters(clusters);
          }, function(err) {
            if (err) throw err;
          });
        }
      })

    });

    //    $(document).foundation({
    //    	  slider: {
    //    	    on_change: function(){
    //    	      // do something when the value changes
    //    	    	console.log('asd');
    //    	    }
    //    	  }
    //    	});


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
      var pad = 20;
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

    function addRect(clustersUI, color, cx, cy) {
      var width = 40;
      var height = 30;
      //      var x = cx - width * 0.5;
      //      var y = cy - height * 0.5;
      var elem = appendSVG(items, "circle"); //creates a circle
      attr(elem, {
        cx: cx,
        cy: cy,
        r: 10,
      });
      style(elem, {
        "stroke": "black",
        "stroke-width": 1,
        "fill": color,
      });
      clustersUI.push({
        x: cx,
        y: cy,
        elem: elem,
        width: width,
        height: height
      });
    }

    main.onclick = function(e) {
      addRect(rectanglesA, "cornflowerblue", e.offsetX, e.offsetY);
    };
    var oldX = Number.NaN;
    var oldY = Number.NaN;
    main.oncontextmenu = function(e) {
      if (oldX === e.offsetX && oldY === e.offsetY) return;
      oldX = e.offsetX;
      oldY = e.offsetY;
      addRect(rectanglesB, "crimson", e.offsetX, e.offsetY);
      e.preventDefault();
    };
  }
]);
