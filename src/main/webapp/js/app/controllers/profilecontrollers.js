/**
 * Profile Controller.
 */
photoplatformControllers.controller('ProfileCtrl', ['$scope', '$routeParams', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $routeParams, $rootScope, $location, $http, $cookieStore, UserService, $route) {
        //if user is not logged in or authorized redirect to login page
        var user = $cookieStore.get('user');
        if (undefined == user || !$rootScope.isLoggedIn()) {
            $location.path("/login");
            return;
        }
    }]);

/**
 * Photographer Controller.
 */
photoplatformControllers.controller('PhotographerCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {
        var user = $cookieStore.get('user');
        //if user is not logged in or logged in redirect to login page
        if (undefined == user || !$rootScope.isLoggedIn()) {
            $location.path("/login");
            return;
        } else if ($rootScope.isPhotographer()) {
            // User is already a photographer
            $location.path("/profile");
            return;
        }

        /**
         * Become a Photographer.
         * @param user the user
         */
        $scope.updatePhotographer = function (user) {
            UserService.updatePhotographer(user).success(function (data) {
                console.log("Update Photographer");
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };
    }]);