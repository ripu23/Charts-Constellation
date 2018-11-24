var app = angular.module("mainApp");

app.factory('ShareData', function() {
  let data = {};
  data.domCreated = false;
  data.distances = [];
  data.clusters = [];
  data.chartTypes = [];
  data.users = [];
  data.chartOptions = [];
  data.userOptions = [];
  data.attributeOptions = [];
  data.clusterBoard = [];
  data.dataCoverage = {};
  data.dataCoverage.countAttributes = [];
  data.dataCoverage.AttributeMap = [];
  data.dataCoverage.attributeOccurenceMap = {};
  data.dataCoverage.attributesMap = {};
  data.filters = {};
  data.filters.filterList = [];
  data.allDetails = [];
  data.dataSetId = "olympics";
	return {
		data: data
  }
});
