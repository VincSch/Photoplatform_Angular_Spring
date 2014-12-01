photoplatformControllers.controller('imageCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService) {
        var user = $cookieStore.get('user');
        //if user is not logged in or authorized redirect to login page
        if (undefined == user || !$rootScope.isLoggedIn() || !$rootScope.isAdmin()) {
            $location.path("/login");
            return;
        }

        $scope.upload = function () {
            var param = {};
            ImageService.upload(param)
                .success(function (message) {
                    console.log(message);
                }).error(function (e) {
                    console.log(e);
                });

        }
    }
]);