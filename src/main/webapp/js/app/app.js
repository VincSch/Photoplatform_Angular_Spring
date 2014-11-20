var xAuthTokenHeaderName = 'x-auth-token';
var photoplatform = angular.module('photoplatform', ['ngRoute', 'ngCookies', 'photoplatformControllers', 'ui.bootstrap']);

photoplatform.config(['$routeProvider', '$locationProvider', '$httpProvider',
    function ($routeProvider, $locationProvider, $httpProvider) {
        $routeProvider.when('/profile', {
            templateUrl: '/views/partials/profile/index.html',
            controller: 'ProfileCtrl'
        }).when('/login', {
            templateUrl: '/views/partials/home/login.html',
            controller: 'LoginCtrl'
        }).when('/register', {
            templateUrl: '/views/partials/home/register.html',
            controller: 'RegisterCtrl'
        }).when('/admin', {
            templateUrl: '/views/partials/admin/adminmenu.html',
            controller: 'AdminMenuCtrl'
        }).when('/wellcomeuser', {
            templateUrl: '/views/partials/user/wellcomeUser.html',
            controller: ''
        }).when('/', {
            templateUrl: '/views/partials/home/home.html',
            controller: ''
        }).otherwise({
            redirectTo: '/'
        });

        //OLD routes
        /*
         .when('/profile/edit/:name', {
         templateUrl : '/views/partials/profile/detail.html',
         controller : 'ProfileDetailCtrl'
         })
         '/recipes', {
         templateUrl : '/views/partials/recipe/list.html',
         controller : 'RecipeListCtrl'
         }).when('/profile/recipes', {
         templateUrl : '/views/partials/profile/recipes.html',
         controller : 'ProfileCtrl'
         }).when('/profile/recipebooks', {
         templateUrl : '/views/partials/profile/recipebooks.html',
         controller : 'ProfileCtrl'
         }).when('/recipe/edit/:recipeName', {
         templateUrl : '/views/partials/recipe/detail.html',
         controller : 'RecipeDetailCtrl'
         }).when('/recipe/create', {
         templateUrl : '/views/partials/recipe/create.html',
         controller : 'RecipeCreateCtrl'
         }).when('/recipe/view/:id', {
         templateUrl : '/views/partials/recipe/view.html',
         controller : 'RecipeViewCtrl'
         }).when('/ingredients', {
         templateUrl : '/views/partials/ingredient/list.html',
         controller : 'IngredientListCtrl'
         }).when('/ingredient/edit/:ingredientName', {
         templateUrl : '/views/partials/ingredient/detail.html',
         controller : 'IngredientDetailCtrl'
         }).when('/ingredient/create', {
         templateUrl : '/views/partials/ingredient/create.html',
         controller : 'IngredientCreateCtrl'
         }).when('/recipebooks', {
         templateUrl : '/views/partials/recipebook/list.html',
         controller : 'RecipeBookListCtrl'
         }).when('/recipebook/create', {
         templateUrl : '/views/partials/recipebook/create.html',
         controller : 'RecipeBookCreateCtrl'
         }).when('/recipebook/edit/:recipeBookName', {
         templateUrl : '/views/partials/recipebook/detail.html',
         controller : 'RecipeBookDetailCtrl'
         }).when('/profile/view/:name', {
         templateUrl : '/views/partials/profile/view.html',
         controller : 'ProfileViewCtrl'
         })
         */

        // $locationProvider.hashPrefix('#');

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
                } else {
                    if (status !== 404)
                        $rootScope.error = method + " on " + url + " failed with status " + status;
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
        if ($rootScope.transferSuccess == false || $rootScope.transferSuccess == undefined)
            delete $rootScope.success;
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

    var originalPath = $location.path();
    if ($location.path() !== '')
        $location.path("/login");
    var user = $cookieStore.get('user');
    if (user !== undefined) {
        $rootScope.user = user;
        $http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;

        $location.path(originalPath);
    }

    $rootScope.isLoggedIn = function () {

        if ($rootScope.user === undefined) {
            return false;
        } else {
            return true;
        }
    };

    $rootScope.ADMIN = "ROLE_ADMIN";
    $rootScope.CUSTOMER = "ROLE_CUSTOMER";
    $rootScope.PHOTOGRAPHER = "ROLE_PHOTOGRAPHER"
    $rootScope.isAdmin = function () {
        if ($rootScope.user !== undefined) {
            for (var i = 0; i < $rootScope.user.authorities.length; ++i) {
                if ($rootScope.user.authorities[i].authority == $rootScope.ADMIN)
                    return true;
            }
            ;
        }
        return false;
    };

    $rootScope.isCustomer = function () {
        if ($rootScope.user !== undefined) {
            var isCustomer = false;
            $rootScope.user.authorities.forEach(function (role) {
                if (role.authority == $rootScope.CUSTOMER)
                    isCustomer = true;
            });
        }
        return isCustomer;
    };

    $rootScope.isPhotographer = function () {
        if ($rootScope.user !== undefined) {
            var isPhotograph = false;
            $rootScope.user.authorities.forEach(function (role) {
                if (role.authority == $rootScope.PHOTOGRAPHER)
                    isPhotograph = true;
            });
        }
        return isPhotograph;
    };

    console.log($rootScope.user);

});
