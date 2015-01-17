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
        '$routeParams',
        'UserService',
        'ShoppingListService',
        '$route',
        function ($scope, $rootScope, $location, $http, $cookieStore, $routeParams, UserService, ShoppingListService, $route) {

            var user = $cookieStore.get('user');
            //if user is not logged in or logged in redirect to login page
            if (user == undefined || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            var start = 0;
            var count = 100;
            $scope.noerror = true;

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
            
            /**
             * start purchase process
             */
             $scope.startPurchase = function(){
                ShoppingListService.startPurchase().success(function (data){
                    location.href = data;
                }).error(function () {
            		$scope.noerror = false;
                });
            };
            
            /**
             * complete the puchase process
             */
             $scope.completePurchase = function(){
             	if(($routeParams.paymentId == undefined) || ($routeParams.PayerID == undefined))
             	{
             		$location.path("/cart");
             	}
             	
                ShoppingListService.executePurchase($routeParams.paymentId, $routeParams.PayerID).success(function (){
                	$location.$$search = {};
             		$location.path("/profile/shoppinglist");
                }).error(function () {
            		$scope.noerror = false;
                });
            };

        }]);