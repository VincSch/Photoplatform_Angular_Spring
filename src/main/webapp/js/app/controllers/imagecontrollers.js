photoplatformControllers.controller('ImageCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', 'UserService', 'ImageService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, UserService, ImageService) {
            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            var start = 0;
            var count = 100;

            $scope.copy = angular.copy;
            $scope.images = [];

            $scope.uploadImage = function () {
                var param = $scope.files;
                ImageService.upload(param, $rootScope.user)
                    .success(function (message) {
                        console.log(message);
                    })
                    .error(function (e) {
                        console.log(e);
                    });
            };

            /**
             * Get all images.
             */
            $scope.getAllImages = function () {
                ImageService.getAllImages(start, count).success(function (images) {
                    $scope.images = images;
                }).error(function (data) {
                    $scope.errors = data.errors;
                })
            };

            /**
             * Get all images.
             */
            $scope.deleteImage = function (image, index) {
                ImageService.deleteImage(image.id).success(function (message) {
                    $scope.images.splice(index, 1);
                    $rootScope.success = message;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

        }]);