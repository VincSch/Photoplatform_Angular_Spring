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
            UserService.updatePhotographer(user).success(function (data) {
                console.log("Update Photographer");
            }).error(function (data) {
                $scope.errors = data.errors;
            });
        };
        
        //check param
        $scope.userId = $routeParams.userId;
        if ($scope.userId == undefined) {
            $scope.userId = user.id;
        }

        //get user ProfileData
        UserService.getUserProfileData($scope.userId).success(function (responseUserProfileData) {
            $scope.userProfileData = responseUserProfileData;
            $scope.roles = responseUserProfileData.roles;
        }).error(function (error) {
            console.log(error);
            $rootScope.error(error);
        });

        //save input from edited field
        $scope.save = function () {
            UserService.updateUserProfileData($scope.userProfileData).success(function (data) {
                $rootScope.success = "Profil erfolgreich aktualisiert";
            }).error(function (data) {
                $scope.errors = data.errors;
            });
            $location.path("/profile/view");
        };
        //cancel fieldinput and reset data in field
        $scope.cancel = function (fieldToCancel) {
            UserService.getUserProfileData($scope.userId).success(function (responseUserProfileData) {
                $scope.userProfileData = responseUserProfileData;
                $scope.roles = responseUserProfileData.roles;
            }).error(function (error) {
                console.log(error);
                $rootScope.error(error);
            });
        };
        //change user password and close modal on success
        $scope.changePassword = function (pw) {
            UserService.changePassword(pw).success(function (user) {
                $rootScope.success = "Passwort erfolgreich geändert";
                $('#pwModal').modal('hide');
                // set the new token
                $rootScope.user = user;
                $http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;
                $cookieStore.put('user', user);
            }).error(function (data) {
                console.log(data);
                $scope.errors = data.errors;
            });
            $location.path("/profile/view");
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
        $scope.showcase = [];

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
         * Get all user collections in showcase
         */
        $scope.getShowcase = function () {
            PhotographerService.getShowcase(start, count).success(function (collections) {
                $scope.showcase = collections;
            });
        };

        /**
         * Create new collection
         * @param collection
         */
        $scope.createCollection = function (collection) {
            PhotographerService.createCollection(collection).success(function (newCollection) {
                $scope.collections.unshift(newCollection);
                $rootScope.success = 'Deine Sammlung ' + newCollection.name + ' wurde erfolgreich erstellt';
                collection = null;
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
            });
        };

        /**
         * Publish collection and show in showcase.
         */
        $scope.updateCollectionShowcase = function (collection, isPublic) {
            PhotographerService.updateCollectionShowcase(collection.id, isPublic).success(function (message) {
                collection.public = isPublic;
                $rootScope.success = message;
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };

        /**
         * Publish collection and show in showcase.
         */
        $scope.deleteCollection = function (collection, index) {
            PhotographerService.deleteCollection(collection.id).success(function (message) {
                $scope.collections.splice(index, 1);
                $rootScope.success = message;
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };
    }]);