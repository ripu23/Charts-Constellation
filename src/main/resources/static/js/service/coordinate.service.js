'use strict'
var app = angular.module("mainApp");

app.service("CoordinateService", function($http, ShareData) {
  this.getCoordinates = function getCoordinates(data) {
      return $http({
        method: 'GET',
        url: '/coordinates/getCoordinates',
        params: {
          descWeight: data.descWeight,
          attrWeight: data.attrWeight,
          chartEncodingWeight: data.chartEncodingWeight,
          colorMap: data.colorMap,
          datasetId: ShareData.data.dataSetId
        }
      })
    },
    this.updateClusters = function updateClusters(data) {
      return $http({
        method: 'GET',
        url: '/coordinates/updateFilter',
        params: {
          filter: data.updatedFilters,
          colorMap: data.colorMap,
          dataRange: data.dateRange,
          datasetId: ShareData.data.dataSetId
        }
      })
    }

})
