var app = angular.module("mainApp");

app.factory('ShareData', function() {
  let data = {};
  data.attrWeight = 1;
  data.chartEncodingWeight = 1;
  data.descWeight = 1;
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
  data.filters = {};
  data.filters.filterList = [];
	return {
		data: data
  }
});
