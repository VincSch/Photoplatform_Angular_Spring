photoplatformControllers.controller('CollectionCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', 'UserService', 'ImageService', 'CollectionService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, UserService, ImageService, CollectionService) {
            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            $scope.collectionName = $routeParams.collectionName;


            $rootScope.goBack = function ($window, $rootScope) {
                $window.history.back();
                scope.$apply();
            }


        }]);