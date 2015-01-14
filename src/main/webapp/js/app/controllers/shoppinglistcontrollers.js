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

    }])
;