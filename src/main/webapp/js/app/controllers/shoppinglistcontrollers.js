/**
 * Image search controller.
 */
photoplatformControllers.controller('ShoppingListCtrl', ['$scope', '$cookieStore', '$rootScope', 'ShoppingListService', '$location', 'md5',
    function ($scope, $cookieStore, $rootScope, ShoppingListService, $location, md5) {

        var user = $cookieStore.get('user');
        //if user is not logged in or logged in redirect to login page
        if (user == undefined || !$rootScope.isLoggedIn()) {
            $location.path("/login");
            return;
        }

        $scope.encodedToken = md5.createHash($rootScope.user.secToken);
        $scope.userId = $rootScope.user.id;

        ShoppingListService.getPurchasedImages().success(function (images) {
            $scope.images = images;
        }).error(function (error) {
            console.log(error);
            $rootScope.error(error);
        });

        /**
         * Get raw image
         * @param imageId
         */
        $scope.downloadImage = function (imageId) {
            ShoppingListService.getRawImage(imageId).success(function (rawImage) {

            }).error(function (data) {
                $scope.errors = data.errors;
            });
        };

    }]);