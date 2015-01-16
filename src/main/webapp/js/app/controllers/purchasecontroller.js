/**
 * Photographer Controller.
 */
photoplatformControllers.controller(
    'PurchaseCtrl',
    [
        '$scope',
        '$rootScope',
        '$location',
        '$http',
        '$cookieStore',
        'UserService',
        'ShoppingListService',
        '$route',
        function ($scope, $rootScope, $location, $http, $cookieStore, UserService, ShoppingListService, $route) {

            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (user == undefined || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            var start = 0;
            var count = 100;

            /**
             * Add image to shopping cart
             * in customImage.drt.js
             */


            $scope.getShoppingCartData = function(){
                ShoppingListService.getShoppingCartData().success(function (data){
                    $scope.shoppingCart = data;
                });
            };
            /**
             * remove image from shopping cart
             */
            $scope.removeFromShoppingCart = function (itemId, index) {
                ShoppingListService.removeFromShoppingCart(itemId).success(function (data) {
                    $rootScope.user.totalItems = data.totalItems;
                    $scope.shoppingCart.totalItems = data.totalItems;
                    $scope.shoppingCart.totalPrice = data.totalPrice;
                    $scope.shoppingCart.purchaseItems.splice(index, 1);
                    $rootScope.success = "Bild wurde erfolgreich aus ihren Warenkorb entfernt";
                });
            };

        }]);