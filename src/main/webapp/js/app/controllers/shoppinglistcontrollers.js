/**
 * Image search controller.
 */
photoplatformControllers.controller('ShoppingListCtrl', ['$scope', '$rootScope', 'ShoppingListService', '$location',
    function ($scope, $rootScope, ShoppingListService, $location) {

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