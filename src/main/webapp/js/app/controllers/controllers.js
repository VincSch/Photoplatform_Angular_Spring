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
