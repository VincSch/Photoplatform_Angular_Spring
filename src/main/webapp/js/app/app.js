var xAuthTokenHeaderName = 'x-auth-token';
var photoplatform = angular.module('photoplatform', ['ngRoute', 'ngCookies', 'photoplatformControllers', 'ui.bootstrap']);

/**
 * App configuration.
 */
photoplatform.config(['$routeProvider', '$locationProvider', '$httpProvider',
    function ($routeProvider, $locationProvider, $httpProvider) {
        // Enable HTML5 strategy (without # in urls)
        $locationProvider.html5Mode(true);

        $routeProvider.when('/profile', {
            templateUrl: '/views/partials/profile/index.html',
            controller: 'ProfileCtrl'
        }).when('/login', {
            templateUrl: '/views/partials/home/login.html',
            controller: 'AuthCtrl'
        }).when('/register', {
            templateUrl: '/views/partials/home/register.html',
            controller: 'AuthCtrl'
        }).when('/admin', {
            templateUrl: '/views/partials/admin/adminmenu.html',
            controller: 'AdminMenuCtrl'
        }).when('/welcomeuser', {
            templateUrl: '/views/partials/user/welcomeuser.html',
            controller: ''
        }).when('/devprofile', {
            templateUrl: '/views/partials/user/devprofile.html',
            controller: 'ProfileCtrl'
        }).when('/devprofile/:userId', {
            templateUrl: '/views/partials/user/devprofile.html',
            controller: 'ProfileCtrl'
        }).when('/becomePhotographer', {
            templateUrl: '/views/partials/profile/becomePhotographer.html',
            controller: 'PhotographerCtrl'
        }).when('/', {
            templateUrl: '/views/partials/home/home.html',
            controller: ''
        }).otherwise({
            redirectTo: '/'
        });

        /* Intercept http errors */
        var interceptor = function ($rootScope, $q, $location) {
            function success(response) {
                return response;
            }

            function error(response) {

                var status = response.status;
                var config = response.config;
                var method = config.method;
                var url = config.url;

                if (status == 403) {
                    $location.path("/login");
                    $rootScope.error = status + " ! Nicht authorisiert";
                }
                else {
                    if (status !== 404) {
                        $rootScope.error = method + " on " + url + " failed with status " + status;
                    }
                }

                return $q.reject(response);
            }

            return function (promise) {
                return promise.then(success, error);
            };
        };

        $httpProvider.responseInterceptors.push(interceptor);

    }]).run(function ($rootScope, $http, $location, $cookieStore, UserService) {

    /* Reset error and sucess when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
        if ($rootScope.transferSuccess == false || $rootScope.transferSuccess == undefined) {
            delete $rootScope.success;
        }
        else {
            $rootScope.transferSuccess = false;
        }
    });

    $rootScope.logout = function () {
        delete $rootScope.user;
        delete $http.defaults.headers.common[xAuthTokenHeaderName];
        $cookieStore.remove('user');
        $location.path("/login");
    };

    /* Try getting valid user from cookie or go to login page */

    var user = $cookieStore.get('user');
    if (user !== undefined) {
        $rootScope.user = user;
        $http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;

        $location.path($location.path());
    }

    $rootScope.isLoggedIn = function () {
        return $rootScope.user !== undefined;
    };

    $rootScope.ADMIN = "ROLE_ADMIN";
    $rootScope.CUSTOMER = "ROLE_CUSTOMER";
    $rootScope.PHOTOGRAPHER = "ROLE_PHOTOGRAPHER";
    $rootScope.hasRole = function (role) {
        if ($rootScope.user !== undefined) {
            for (var i = 0; i < $rootScope.user.authorities.length; ++i) {
                if ($rootScope.user.authorities[i].authority == role) {
                    return true;
                }
            }
        }
        return false;
    };

    $rootScope.isAdmin = function () {
        return $rootScope.hasRole($rootScope.ADMIN);
    };

    $rootScope.isCustomer = function () {
        return $rootScope.hasRole($rootScope.CUSTOMER);
    };

    $rootScope.isPhotographer = function () {
        return $rootScope.hasRole($rootScope.PHOTOGRAPHER);
    };

    console.log($rootScope.user);
    
});
