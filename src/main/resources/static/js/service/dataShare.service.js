var app = angular.module("mainApp");

app.factory('ShareData', function() {
  let data = {};
  data.dataCoverageCoefficient = 1;
  data.encodingCoefficient = 1;
  data.descriptionCoefficient = 1;
	data.distances = [];
  data.coordinates = [];
  data.users = [];
	return {
    data: data
  }
});
