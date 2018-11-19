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
    let attributeOccurenceMap = ShareData.data.dataCoverage.attributeOccurenceMap;
    let attributesMap = ShareData.data.dataCoverage.attributesMap;
    const offSet = $("#charts").offset();
    $scope.users = ShareData.data.users;
    $scope.chartTypes = ShareData.data.chartTypes;
    $scope.dataCoverageCoefficient = ShareData.data.dataCoverageCoefficient;
    $scope.encodingCoefficient = ShareData.data.encodingCoefficient;
    $scope.descriptionCoefficient = ShareData.data.descriptionCoefficient;
    $scope.timelineLeft;
    $scope.timelineRight;
    $scope.chartOptions = ShareData.data.chartOptions;
    $scope.userOptions = ShareData.data.userOptions;
    $scope.attributeOptions = ShareData.data.attributeOptions;
    $scope.filters = ShareData.data.filters;
    $scope.clusterBoard = ShareData.data.clusterBoard;
    $scope.dataCoverage = ShareData.data.dataCoverage;
    $scope.dataCoverage.countAttributes = ShareData.data.dataCoverage.countAttributes;
    $scope.dataCoverage.AttributeMap = ShareData.data.dataCoverage.AttributeMap;
    $scope.colorSequence = [];
    var bubbles = new BubbleSet();
    var main = document.getElementById("main");
    var items = appendSVG(main, "g");
    var debug = appendSVG(main, "g");
    var tooltips;
    bubbles.debug(false);
    let ready = false;
    let domCreated;
    $("#main").selectable({
      classes: {
        "ui-selectable": "highlight"
      },
      selected: function(event, ui) {
        console.log(ui);
      }
    });


    ChartService.getChartTypes().then(function(data) {
      alertify.success('Successfully imported chartTypes');
      $scope.chartTypes = data.data.chartTypes;
      ShareData.chartTypes = data.data.chartTypes;
    }, function(err) {
      alertify.error('Something is wrong with API --> ChartService --> getCharts');
      if (err) throw err;
    });

    ChartService.getAllAttributes().then(function(data) {
      $scope.attributeList = data.data.attributesSet;
      ShareData.attributeList = data.data.attributesSet;
    }, function(err) {
      alertify.error('Something is wrong with API --> ChartService --> getAllAttributes');
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
      // console.log($('#timelineLeft').attr('value'));
      // console.log($('#timelineRight').attr('value'));


      if (!ready || domCreated) {
        var updatedAttrWeight = $('#attrWeight').attr('aria-valuenow');
        var updatedDescWeight = $('#descWeight').attr('aria-valuenow');
        var updatedChartEncodingWeight = $('#chartEncodingWeight').attr('aria-valuenow');
        var colorMap = populateColorMap();
        var dataSend = {
          "descWeight": parseFloat(updatedDescWeight),
          "attrWeight": parseFloat(updatedAttrWeight),
          "chartEncodingWeight": parseFloat(updatedChartEncodingWeight),
          "colorMap": JSON.stringify(colorMap)
        }
        if (updatedAttrWeight && updatedDescWeight && updatedChartEncodingWeight) {
          CoordinateService.getCoordinates(dataSend).then(function(data) {
            removePaths();
            removeAllChilds(items);
            items = appendSVG(main, "g");
            clusters = data.data.coordinatesList;
            $scope.clusterBoard = data.data.clusters;
            ShareData.clusters = data.data.coordinatesList;
            attributesMap = data.data.dataCoverage.attributesMap;
            $scope.dataCoverage.countAttributes = Object.keys(data.data.dataCoverage.attributesMap).sort(function(a, b) {
              return data.data.dataCoverage.attributesMap[b] - data.data.dataCoverage.attributesMap[a]
            });
            attributeOccurenceMap = data.data.dataCoverage.attributeOccurenceMap;
            createClusters(clusters);
          }, function(err) {
            if (err) throw err;
          });
        }
        ready = true;
      }
    });

    function populateColorMap() {
      var colorMap = [];
      if ($scope.users) {
        _.forEach($scope.users, function(user) {
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
      let counter = 0;
      _.forEach(clustersArray, function(clusters, i) {
        clustersUI[i] = [];
        _.forEach(clusters, function(cluster) {
          addRect(clustersUI[i], "grey", cluster.point.x, cluster.point.y, cluster.color, counter, cluster.userName, cluster.chartType, cluster.chartName);
          counter++;
        })
      });
      createPaths();
    }
    //Creation of paths and append to SVG;
    function createPaths() {
      paths = [];
      _.forEach(clustersUI, function(cluster, idx) {
        var svg = appendSVG(main, "path");
        // idx += 1;
        attr(svg, {
          id: "path" + idx
        });
        paths.push({
          svg: svg
        })
      });
      update();
    }

    //Updates the padding of each cluster
    function update() {
      if (clustersUI[1] == undefined) {
        updateOutline(clustersUI[0], clustersUI[0], "grey", paths[0].svg);
        updateOutline(clustersUI[0], clustersUI[0], "grey", paths[0].svg);
      } else {
        _.forEach(clustersUI, function(cluster1, idx1) {
          _.forEach(clustersUI, function(cluster2, idx2) {
            if (cluster1 != cluster2) {
              updateOutline(cluster1, cluster2, "grey", paths[idx1].svg);
              updateOutline(cluster2, cluster1, "grey", paths[idx2].svg);
            }
          })
        })
      }
      domCreated = true;
    }

    $(function() {
      $(document).tooltip({
        items: "circle",
        content: function() {
          var element = $(this);
          var chartName = element.attr('chartName');
          return "<img class='map' src='../images/" + chartName + ".png'  />";
        }
      });
    });

    $scope.clearFilter = function() {
      $scope.filters.filterList = [];
      var updatedAttrWeight = $('#attrWeight').attr('aria-valuenow');
      var updatedDescWeight = $('#descWeight').attr('aria-valuenow');
      var updatedChartEncodingWeight = $('#chartEncodingWeight').attr('aria-valuenow');
      var colorMap = populateColorMap();
      var dataSend = {
        "descWeight": parseFloat(updatedDescWeight),
        "attrWeight": parseFloat(updatedAttrWeight),
        "chartEncodingWeight": parseFloat(updatedChartEncodingWeight),
        "colorMap": JSON.stringify(colorMap)
      }
      if (updatedAttrWeight && updatedDescWeight && updatedChartEncodingWeight) {
        CoordinateService.getCoordinates(dataSend).then(function(data) {
          removeAllChilds(items);
          removePaths();
          main = document.getElementById("main");
          items = appendSVG(main, "g");
          clusters = data.data;
          ShareData.clusters = data.data;
          createClusters(clusters);
        }, function(err) {
          if (err) throw err;
        });
      }
      ready = true;

    }


    $scope.highlightCluster = function(idx) {
      $("#path" + idx).css("fill", "blue");

    }

    $scope.removeCss = function(idx) {
      $("#path" + idx).css("fill", "");
    }

    $scope.highlightCirclesForUser = function(userId) {
      $("circle[userId=" + "'" + userId.userName + "']").css({
        "stroke": "black",
        "stroke-width": 3
      })
    }

    $scope.removeHighlightFromCircles = function(userId) {
      $("circle[userId=" + "'" + userId.userName + "']").removeAttr("style");
    }

    $scope.dataCoverageIntersectionStart = function(val) {
      console.log(val);
      if (attributeOccurenceMap[val]) {
        $scope.colorSequence = ClusterService.getSequentialColors(attributeOccurenceMap[val].length);
        attributeOccurenceMap
        attributesMap
        _.forEach(attributeOccurenceMap[val], function(val, key) {
          var _color = "ffa500";
          if (attributesMap[val]) {
            _color = $scope.colorSequence[attributesMap[val] % $scope.colorSequence.length];
          }
          $('#data-coverage-member-' + val).css({
            "background-color": "#" + _color
          })
        })
      }

    }

    $scope.dataCoverageIntersectionEnd = function(val) {
      console.log(val);
      if (attributeOccurenceMap[val]) {
        _.forEach(attributeOccurenceMap[val], function(val, key) {
          $('#data-coverage-member-' + val).removeAttr("style");
        })
      }
    }



    $scope.highlightCirclesForChartTypes = function(charts) {
      $("circle[chartType=" + "'" + charts + "']").css({
        "stroke": "black",
        "stroke-width": 3
      })
    }

    $scope.removeCirclesForChartTypes = function(charts) {
      $("circle[chartType=" + "'" + charts + "']").removeAttr("style");

    }

    $scope.updateFilter = function() {
      populateWeight();
      var colorMap;
      var toBeSent = {};
      var obj = angular.fromJson(angular.toJson($scope.filters));
      _.forEach(obj.filterList, function(filter, key) {
        if (filter.map && filter.map.users) {
          colorMap = getColorMapForUpdate(filter.map.users);
        }
      });
      if (!colorMap) {
        colorMap = populateColorMap();
      }
      toBeSent.updatedFilters = obj;
      toBeSent.colorMap = JSON.stringify(colorMap);
      CoordinateService.updateClusters(toBeSent).then(function(data) {

        removePaths();
        removeAllChilds(items);
        main = document.getElementById("main");
        items = appendSVG(main, "g");
        clusters = data.data.coordinatesList;
        var newItems = data.data.clusters;
        $scope.clusterBoard = data.data.clusters;
        ShareData.clusters = data.data.coordinatesList;
        attributesMap = data.data.dataCoverage.attributesMap;
        $scope.dataCoverage.countAttributes = Object.keys(data.data.dataCoverage.attributesMap).sort(function(a, b) {
          return data.data.dataCoverage.attributesMap[b] - data.data.dataCoverage.attributesMap[a]
        });
        attributeOccurenceMap = data.data.dataCoverage.attributeOccurenceMap;
        createClusters(clusters);
        domCreated = false;
      }, function(err) {
        if (err) throw err;
      });

    }

    function removePaths() {
      var length = main.childNodes.length;
      if (length && length > 0) {
        _.forEach(main.childNodes, function(node, key) {
          if (node && node.nodeName && node.nodeName === "path") {
            main.removeChild(main.childNodes[key]);
            removePaths();
          }
        })
      }
    }

    function getColorMapForUpdate(data) {
      var colorMap = [];
      if (data) {
        _.forEach(data, function(userData, key) {
          _.forEach($scope.users, function(user, key) {
            if (user.userName === userData) {
              var toBePushed = {
                userName: user.userName,
                color: user.color
              }
              colorMap.push(toBePushed)
            }
          })
        });
      }
      return colorMap;
    }

    $scope.$watch('chartOptions', function(newVal, oldVal, scope) {
      populateChartFilter();
    }, true);

    $scope.$watch('userOptions', function(newVal, oldVal, scope) {
      populateUserFilter();
    }, true);

    $scope.$watch('attributeOptions', function(newVal, oldVal, scope) {
      populateAttributeFilter();
    }, true);

    function populateChartFilter() {
      if ($scope.chartOptions.length > 0) {
        ShareData.chartOptions = $scope.chartOptions;
        $scope.filters.filterList = _.reject($scope.filters.filterList, function(val, key) {
          if (val && val.map && val.map.charts) return true;
        });
        let chartObj = {};
        chartObj.charts = [];
        _.forEach($scope.chartOptions, function(option, key) {
          if (option === true) {
            chartObj.charts.push($scope.chartTypes[key]);
          }
        });
        $scope.filters.filterList.push({
          map: chartObj
        });
      }
    }

    function populateUserFilter() {
      if ($scope.userOptions.length > 0) {
        ShareData.userOptions = $scope.userOptions;
        $scope.filters.filterList = _.reject($scope.filters.filterList, function(val, key) {
          if (val && val.map && val.map.users) return true;
        });
        let userObj = {};
        userObj.users = [];
        _.forEach($scope.userOptions, function(user, key) {
          if (user === true) {
            userObj.users.push($scope.users[key].userName);
          }
        })
        $scope.filters.filterList.push({
          map: userObj
        });
      }
    }

    function populateAttributeFilter() {
      if ($scope.attributeOptions.length > 0) {
        ShareData.attributeOptions = $scope.attributeOptions;
        $scope.filters.filterList = _.reject($scope.filters.filterList, function(val, key) {
          if (val && val.map && val.map.attributes) return true;
        });
        let attributeObj = {};
        attributeObj.attributes = [];
        _.forEach($scope.attributeOptions, function(attribute, key) {
          if (attribute === true) {
            attributeObj.attributes.push($scope.attributeList[key]);
          }
        })
        $scope.filters.filterList.push({
          map: attributeObj
        });
      }
    }

    function populateWeight() {
      $scope.filters.filterList = _.reject($scope.filters.filterList, function(val, key) {
        if (val && val.map && val.map.weights) return true;
      });
      var updatedAttrWeight = $('#attrWeight').attr('aria-valuenow');
      var updatedDescWeight = $('#descWeight').attr('aria-valuenow');
      var updatedChartEncodingWeight = $('#chartEncodingWeight').attr('aria-valuenow');
      let weightObj = {};
      weightObj.weights = [];
      weightObj.weights.push(updatedDescWeight);
      weightObj.weights.push(updatedAttrWeight);
      weightObj.weights.push(updatedChartEncodingWeight);
      if (weightObj.weights.length > 0) {
        $scope.filters.filterList.push({
          map: weightObj
        });
      }
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
      var pad = 2;
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

    function addRect(clustersUI, clusterColor, cx, cy, pointColor, circleId, userId, chartType, chartName) {
      var width = 40;
      var height = 30;
      var elem = appendSVG(items, "circle"); //creates a circle
      let tooltipUrl = ClusterService.getImageUrl().tooltipUrl;
      attr(elem, {
        cx: cx,
        cy: cy,
        r: 8,
        fill: "#" + pointColor,
        id: "circle" + circleId,
        userId: userId,
        chartName: chartName,
        chartType: chartType
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
