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
    }
]);

/**
 * Photographer Controller.
 */
photoplatformControllers.controller('PhotographerCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', 'PhotographerService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, PhotographerService, $route) {

        var start = 0;
        var count = 100;

        // Photographer collection
        $scope.collections = [];
        $scope.copy = angular.copy;

        /**
         * Get all user collections
         */
        $scope.getAllCollections = function () {
            PhotographerService.getCollections(start, count).success(function (collections) {
                $scope.collections = collections;
            });
        };

        /**
         * Create new collection
         * @param collection
         */
        $scope.createCollection = function (collection) {
            PhotographerService.createCollection(collection).success(function (newCollection) {
                $scope.collections.unshift(newCollection);
                $rootScope.success = 'Deine Sammlung <a href="#' + newCollection.collectonid + '">' + newCollection.name + '</a> wurde erfolgreich erstellt';
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };

        /**
         * Create new collection
         * @param collection
         */
        $scope.updateCollection = function (editCollection, originCollection, status) {
            PhotographerService.updateCollection(editCollection).success(function (collection) {
                angular.extend(originCollection, collection);
                status.editing = false;
                $rootScope.success = 'Erfolgreich aktualisiert';
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };

        /**
         * Publish collection and show in showcase.
         */
        $scope.publishCollection = function (collection) {
            // TODO: implement me
        };
    }]);