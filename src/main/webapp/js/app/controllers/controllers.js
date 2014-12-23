var photoplatformControllers = angular.module('photoplatformControllers', []);

/**
 * Authentication Controller.
 */
photoplatformControllers.controller('AuthCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

        /**
         * Login user.
         * @param email the username
         * @param password the password
         */
        $scope.login = function (email, password) {
            UserService.login(email, password).success(function (user) {
                $rootScope.user = user;
                $http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;
                $cookieStore.put('user', user);
                console.log(user);
                if ($rootScope.isCustomer())
                    $location.path("/profile");
                if ($rootScope.isPhotographer())
                    $location.path("/profile");
                if ($rootScope.isAdmin())
                    $location.path("/profile");
            }).error(function (data) {
                $scope.errors = data.errors;
            });
        };

        /**
         * Register user
         * @param user  the user
         */
        $scope.register = function (user) {
            UserService.register(user).success(function (data) {
                $location.path("/login");
                $rootScope.success = "Du hast dich erfolgreich registriert. Du kannst dich nun mit deinen Daten anmelden!";
                $rootScope.transferSuccess = true;
            }).error(function (data) {
                $scope.errors = data.errors;
            });
        };
    }]);

/**
 * CarouselDemoCtrl.
 */
photoplatform.controller('CarouselDemoCtrl', function ($scope) {
    var slides = $scope.slides = [];
    slides.push({
        image: 'img/car1.jpg',
        text: ['More', 'Extra', 'Lots of', 'Surplus'] + ' ' +
        ['Cats', 'Kittys', 'Felines', 'Cutes']
    });
    slides.push({
        image: 'img/car2.jpg',
        text: ['More', 'Extra', 'Lots of', 'Surplus'] + ' ' +
        ['Cats', 'Kittys', 'Felines', 'Cutes']
    });
    slides.push({
        image: 'img/car3.jpg',
        text: ['More', 'Extra', 'Lots of', 'Surplus'] + ' ' +
        ['Cats', 'Kittys', 'Felines', 'Cutes']
    });
});

/**
 * CarouselDemoCtrl.
 */
photoplatform.controller('ImageSearchCtrl', ['$scope', '$rootScope', 'ImageService',
    function ($scope, $rootScope, ImageService) {

        /**
         * Search images one image by image id.
         */
        $scope.searchData = null;
        $scope.foundedMsg = null;
        $scope.showSearchResult = false;
        $scope.showClearButton = false;
        $scope.foundedImages = [];

        $scope.searchImage = function (value) {
            ImageService.searchImages(value).success(function (foundedImages) {
                $scope.foundedImages = foundedImages;
                $scope.showSearchResult = true;
                if (foundedImages.length == 1) {
                    $scope.foundedMsg = foundedImages.length + " Bild gefunden!"
                } else {
                    $scope.foundedMsg = foundedImages.length + " Bilder gefunden!"
                }
                $scope.showClearButton = true;
            }).error(function (data) {
                $scope.log(data.errors);
                $scope.errors = data.errors;
            })
        };

        $scope.clear = function () {
            //clear array.
            while ($scope.foundedImages.length > 0) {
                $scope.foundedImages.pop();
            }
            $scope.showSearchResult = false;
            $scope.showClearButton = false;
            $scope.foundedMsg = null;
            $scope.searchData = null;
        };

        $scope.images = [];
        $scope.searchAllImages = function () {
            ImageService.searchImages("*").success(function (foundedImages) {
                $scope.images = foundedImages;
            }).error(function (data) {
                $scope.errors = data.errors;
                $scope.log(data.errors);
            })
        };

    }]);
