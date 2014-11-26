var photoplatformControllers = angular.module('photoplatformControllers', []);

/**
 * Authentication Controller.
 */
photoplatformControllers.controller('AuthCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

        /**
         * Login user.
         * @param username the username
         * @param password the password
         */
        $rootScope.login = function (username, password) {
            UserService.login(username, password).success(function (user) {
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
            }).error(function (error) {
            });
        };

        /**
         * Register user
         * @param user  the user
         */
        $rootScope.register = function (user) {
            UserService.register(user).success(function (data) {
                $location.path("/login");
                $rootScope.success = "Du hast dich erfolgreich registriert. Du kannst dich nun mit deinem Benutzernamen anmelden!";
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
 * Admin Menu Controller.
 */
photoplatformControllers.controller('AdminMenuCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {
        var user = $cookieStore.get('user');
        //if user is not logged in or authrized redirect to login page
        if (undefined == user || !$rootScope.isLoggedIn() || !$rootScope.isAdmin()) {
            $location.path("/login");
            return;
        } else {
            var start = 0;
            var count = 100;
            UserService.getUsers(start, count).success(function (users) {
                $scope.users = users;
            }).error(function (error) {
                console.log(error);
            });
        }
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
        $rootScope.becomePhotographer = function (user) {
            UserService.updatePhotographer(user).success(function (data) {
               console.log("Update Photographer");
            }).error(function (data) {
                $scope.errors = data.errors;
            })
        };
    }]);
