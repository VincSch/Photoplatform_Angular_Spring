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
photoplatformControllers.controller('PhotographerCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', 'PhotographerService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, PhotographerService, $route) {

        /**
         * Become Photographer
         * @param user photographer
         */
        $scope.becomePhotographer = function (user) {
            UserService.becomePhotographer(user).success(function (data) {
                $location.path("/profile");
                $rootScope.success = "Du hast dich erfolgreich als Fotograf beworben. Ein Admin muss zuerst dein Account freischalten!";
                $rootScope.transferSuccess = true;
            }).error(function (data) {
                $scope.errors = data.errors;
            });
        };

        /**
         * Update a Photographer.
         * @param user the user
         */
        $scope.updatePhotographer = function (user) {
            PhotographerService.updatePhotographer(user).success(function (data) {
                console.log("Update Photographer");
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };

        /**
         * Create new collection
         * @param collection
         */
        $scope.createCollection = function(collection) {
            PhotographerService.createCollection(collection.name, collection.description).success(function (data) {
                console.log("Create Collection");
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        }
    }]);