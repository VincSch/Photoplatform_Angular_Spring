/**
 * Image search controller.
 */
photoplatformControllers.controller('ShoppingListCtrl', ['$scope', '$rootScope', 'ShoppingListService', '$location',
    function ($scope, $rootScope, ShoppingListService, $location) {

        $scope.encodedToken = encode($rootScope.user.secToken);

        function encode(token){
            var replacedAt = token.replace("@", "(at)");
            var replacedDP = replacedAt.replace(".", "(dot)");
            var encodedToken = replacedDP.replace(":", "(colon)");
            return encodedToken
        }

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