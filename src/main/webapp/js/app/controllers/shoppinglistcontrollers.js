/**
 * Image search controller.
 */
photoplatformControllers.controller('ShoppingListCtrl', ['$scope', '$rootScope', 'ShoppingListService', '$location', 'md5',
    function ($scope, $rootScope, ShoppingListService, $location, md5) {

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