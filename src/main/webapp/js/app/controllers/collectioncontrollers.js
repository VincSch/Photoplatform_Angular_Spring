photoplatformControllers.controller('CollectionCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', 'UserService', 'ImageService', 'CollectionService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, UserService, ImageService, CollectionService) {

            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }
            $scope.collectionId = $routeParams.collectionId;
            $scope.collectionName = $routeParams.collectionName;
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
                CollectionService.getCollectionsImages($scope.collectionId, start, count).success(function (images) {
                    $scope.images = images;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
            };

            /**
             * Delete one image by image id.
             */
            $scope.deleteImage = function (image, index) {
                CollectionService.deleteImage($scope.collectionId, image.id).success(function (message) {
                    $scope.images.splice(index, 1);
                    $rootScope.success = message;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

            /**
             * Update one image by image id.
             */
            $scope.updateImage = function (editImage, originImage, status) {
                //replace comma by dot.
                editImage.price = ("" + editImage.price).replace(",", ".");
                ImageService.updateImage(editImage).success(function (image) {
                    angular.extend(originImage, image);
                    status.editing = false;
                    $rootScope.success = image.messageSuccess;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

        }]);