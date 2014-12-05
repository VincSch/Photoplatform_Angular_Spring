photoplatformControllers.controller('ImageCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', 'UserService', 'ImageService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, UserService, ImageService) {
            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            $scope.uploadImage = function () {
                var param = $scope.files;
                ImageService.upload(param, $rootScope.user)
                    .success(function (message) {
                        console.log(message);
                    })
                    .error(function (e) {
                        console.log(e);
                    });
            }
        }]);