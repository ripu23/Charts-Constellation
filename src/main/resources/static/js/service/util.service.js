var app = angular.module("mainApp");
app.service("ColorService", function() {
  this.getColors = function(num) {
    if(num > 0 && num < 66){
      return palette('mpn65', num);
    }
    return null;
  }
});
