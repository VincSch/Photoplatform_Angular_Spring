/**
 * ViewController for admin page.
 */
photoplatformControllers.controller('AdminMenuCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService) {
        var user = $cookieStore.get('user');
        //if user is not logged in or authorized redirect to login page
        if (undefined == user || !$rootScope.isLoggedIn() || !$rootScope.isAdmin()) {
            $location.path("/login");
            return;
        } else {
            //Photograph table.
            $scope.photographTableCurrentPage = 1;
            $scope.photographTableNumPerPage = 5;
            UserService.getDisabledUsersByRole($rootScope.PHOTOGRAPHER).success(function (users) {
                $scope.photographTablePhotographs = users;
                $scope.photographTableTotalItems = users.length;
                $scope.photographTableShowPagination = users.length > $scope.photographTableNumPerPage;

                // Set watch on pagination numbers
                $scope.$watch('photographTableCurrentPage + photographTableNumPerPage', function () {
                    var begin = (($scope.photographTableCurrentPage - 1) * $scope.photographTableNumPerPage);
                    var end = begin + $scope.photographTableNumPerPage;
                    $scope.photographTableFilteredUsers = $scope.photographTablePhotographs.slice(begin, end);
                });
            }).error(function (error) {
                console.log(error);
                $rootScope.error(error);
            });
            $scope.photographTablePageChanged = function () {
                //check if max count was achieved, than load next photographs.
                //not implemented yet.
            };

            //user table
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
                $rootScope.error(error);
            });

            $scope.userTablePageChanged = function () {
                //check if max count was achieved, than load next 100 users.
                //not implemented yet.
            };
        }
    }
]);

