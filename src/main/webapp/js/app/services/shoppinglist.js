/**
 * ShoppingListService.
 * add to cat remove from cart
 */
angular.module('photoplatform').factory('ShoppingListService', ['$http', '$rootScope',
    function ($http, $rootScope) {

        var urlBase = '/api/';

        return {
            /**
             * Get all shopping cart data
             * @returns {HttpPromise}
             */
            getShoppingCartData : function () {
                return $http.get(urlBase + "purchases");
            },
            
            /**
             * Get the number of items in ShoppingCart
             * @returns {HttpPromise}
             */
            getItemsInShoppingCart : function () {
                return $http.get(urlBase + "purchases/imagenumber");
            },

            /**
             * Add item to shopping cart
             * @param imageId
             * @returns {*}
             */
            addToShoppingCart: function (imageId) {
                return $http({
                    url: urlBase + 'purchases',
                    method: "POST",
                    params: {
                        imageId: imageId
                    }
                });
            },

            /**
             * Remove item from shopping cart
             * @param imageId
             * @returns {HttpPromise}
             */
            removeFromShoppingCart: function (imageId) {
                return $http.delete(urlBase + "purchases/" + imageId);
            },

            /**
             * Starts the payment process through paypal
             * @returns redirect URL to paypal
             */
			startPurchase : function() {
                return $http.get(urlBase + 'purchase/paypal');
			},
			
            /**
             * completes the paypal purchase
             * @param paymentId
             * @param payerID
             */
			executePurchase : function(paymentId, payerId) {
                return $http({
                    url: urlBase + 'purchase/paypal/execute',
                    method: "POST",
                    params: {'paymentId': paymentId, 'payerId': payerId}
                });
			},
			
            /**
             * Get image as bytes
             *
             * @param image id
             * @returns {HttpPromise}
             */
            getRawImage: function (imageId) {
                $http.defaults.headers.common[xAuthTokenHeaderName] = $rootScope.user.secToken;
                return $http.get(urlBase + 'image/' + imageId);
            },

            /**
             * Get collection
             *
             * @param name
             * @param description
             * @returns {HttpPromise}
             */
            getPurchasedImages: function () {
                return $http.get(urlBase + 'users/images');
            }
        }
    }]);