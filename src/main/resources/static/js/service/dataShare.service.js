var app = angular.module("mainApp");

app.factory('ShareData', function() {
  let data = {};
  data.attrWeight = 1;
  data.chartEncodingWeight = 1;
  data.descWeight = 1;
  data.distances = [];
  data.clusters = [];
  data.users = [];
  data.chartTypes = [];
  data.users = [];
	return {
		data: data
  }
});
