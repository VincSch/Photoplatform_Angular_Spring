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

        $scope.userId = $routeParams.userId;

        if ($scope.userId == undefined) {
            $scope.userId = user.id;
        }

        UserService.getUserProfileData($scope.userId).success(function (responseUserProfileData) {
            $scope.userProfileData = responseUserProfileData;
        }).error(function (error) {
            console.log(error);
            $rootScope.error(error);
        });
    }]);