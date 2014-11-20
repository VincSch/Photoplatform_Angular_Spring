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

photoplatformControllers.controller('AdminMenuCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {
        var user = $cookieStore.get('user');
        //if user is not logged in or authorized redirect to login page
        if (undefined == user || !$rootScope.isLoggedIn() || !$rootScope.isAdmin()) {
            $location.path("/login");
            return;
        } else {
            $scope.userTableSearchUsername = "";
            $scope.userTableCurrentPage = 1;
            $scope.userTableNumPerPage = 5;
            var start = 0;
            var count = 100;
            UserService.getEnabledUsers(start, count).success(function (users) {
                $scope.userTableUsers = users;
                $scope.userTableTotalItems = users.length;
                $scope.userTableShowPagination = users.length > $scope.userTableNumPerPage;

                // Set watch on pagination numbers
                $scope.$watch('userTableCurrentPage + userTableNumPerPage', function () {
                    var begin = (($scope.userTableCurrentPage - 1) * $scope.userTableNumPerPage);
                    var end = begin + $scope.userTableNumPerPage;
                    $scope.userTableFilteredUsers = $scope.userTableUsers.slice(begin, end);
                });
            }).error(function (error) {
                console.log(error);
            });

            $scope.userTablePageChanged = function () {
                //check if max count was achieved, than load next 100 users.
                //not implemented yet.
            };

            //Photograph table.
            $scope.photographTableCurrentPage = 1;
            $scope.photographTableNumPerPage = 5;
            UserService.getDisabledUsersByRole($rootScope.PHOTOGRAPHER).success(function (users) {
                $scope.photographTablePhotographs = users;
                $scope.photographTableTotalItems = users.length;
                $scope.photographTableShowPagination = users.length > $scope.photographTableNumPerPage;

                // Set watch on pagination numbers
                $scope.$watch('userTableCurrentPage + userTableNumPerPage', function () {
                    var begin = (($scope.photographTableCurrentPage - 1) * $scope.photographTableNumPerPage);
                    var end = begin + $scope.photographTableNumPerPage;
                    $scope.photographTableFilteredUsers = $scope.photographTablePhotographs.slice(begin, end);
                });
            }).error(function (error) {
                console.log(error);
            });

            $scope.photographTablePageChanged = function () {
                //check if max count was achieved, than load next photographs.
                //not implemented yet.
            };

        }
    }]);