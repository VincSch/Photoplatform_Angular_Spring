var photoplatformControllers = angular.module('photoplatformControllers', []);

photoplatformControllers.controller('LoginCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

        $rootScope.login = function (username, password) {
            var hashedPw = MD5(password);
            UserService.login(username, hashedPw).success(function (user) {
                $rootScope.user = user;
                $http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;
                $cookieStore.put('user', user);
                console.log(user);
                if ($rootScope.isCustomer())
                    $location.path("/profile");
                if ($rootScope.isPhotographer())
                    $location.path("/profile");
                if ($rootScope.isAdmin())
                    $location.path("/admin");
            }).error(function (error) {
            });
        };
    }]);

photoplatformControllers.controller('RegisterCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

        $rootScope.register = function (username, password, email) {
            var hashedPw = MD5(password);
            var user = {'username': username, 'password': hashedPw, 'email': email};
            UserService.register(user).success(function () {
                $location.path("/login");
                $rootScope.success = "Du hast dich erfolgreich registriert. Du kannst dich nun mit deinem Benutzernamen anmelden!";
                $rootScope.transferSuccess = true;
            }).error(function (error) {
            });
        };
    }]);