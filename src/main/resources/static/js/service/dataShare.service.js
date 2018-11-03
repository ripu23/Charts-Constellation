var app = angular.module("mainApp");

app.factory('ShareData', function() {
	let distances = {};
  let coordinates = {};
  let users = {};
	distances.main = [];
  coordinates.main = [];
  users.main = [];
	return {
    distances,
    coordinates,
    users
  }
});
