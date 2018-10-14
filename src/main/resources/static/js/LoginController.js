var app = angular.module("loginApp", []);

app.controller("LoginController", function ($scope, $http, $window) {

    $scope.register = {};
    $scope.userName = "";
    $scope.password = "";
    $scope.loginMessage = "";
    $scope.registerMessage = "";
    
    $('#login-form-link').click(function (e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function (e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });


    $scope.login = function () {
        
        $http.get("/login", {params: { userName: $scope.userName, password: $scope.password}}).then(function (response) {
        	
        			if (response && response.data && response.status == 200) {
        				$window.location.href = "/home";	
        			}else{
        				console.log("no user");
        				$scope.loginMessgae = "User not found"
        			}
        		}, function(err){
        		alert("Something is wrong with the api.")
        });
    }

    $scope.register = function () {
        var data = {
            userName: $scope.register.userName,
            password: $scope.register.password
        };
        $http.post("/register", data).then(function (response) {
            if (response.data && response.data.id) {
                $scope.registerMessage = "Registered successfully, please log in."
            }

        }, function (err) {
            alert('Somethings wrong with api.')
            $scope.registerMessage = "Could not create the user";
        });
    }

})
