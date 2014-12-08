photoplatformControllers.controller('CollectionCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', 'UserService', 'ImageService', 'CollectionService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, UserService, ImageService, CollectionService) {

            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            $scope.collectionName = $routeParams.collectionName;
            $scope.collectionId = 1;
            var start = 0;
            var count = 100;

            // Photographer collection
            $scope.collections = [];
            $scope.showcase = [];

            $scope.copy = angular.copy;

            /**
             * Get all collections images.
             */
            $scope.getCollectionsImages = function () {
                CollectionService.getCollectionsImages($scope.collectionId, start, count).success(function (collections) {
                    $scope.collections = collections;
                });
            };

        }]);