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
        //check param
        $scope.userId = $routeParams.userId;
        if ($scope.userId == undefined) {
            $scope.userId = user.id;
        }

        UserService.getUserProfileData($scope.userId).success(function (responseUserProfileData) {
            $scope.userProfileData = responseUserProfileData;
            $scope.roles = responseUserProfileData.roles;
        }).error(function (error) {
            console.log(error);
            $rootScope.error(error);
        });

        $scope.save = function () {
            UserService.updateUserProfileData($scope.userProfileData).success(function () {
                $rootScope.success = "Profil erfolgreich aktualisiert";
            }).error(function (error) {
                console.log(error);
                $rootScope.error(error);
            });
            $location.path("/profile/view");
        };

        $scope.cancel = function () {

        };

        $scope.changePassword = function (pw) {
                    pw.id = $scope.userId;
                    UserService.changePassword(pw).success(function () {
                        $rootScope.success = "Passwort erfolgreich geändert";
                    }).error(function (error) {
                        console.log(error);
                        $rootScope.error(error);
                    });
                    $location.path("/profile/view");
        };

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