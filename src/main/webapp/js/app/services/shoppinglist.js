/**
 * ShoppingListService.
 * add to cat remove from cart
 */
angular.module('photoplatform').factory('ShoppingListService', ['$http', '$rootScope',
    function ($http, $rootScope) {

        var urlBase = '/api/';

        return {
            getShoppingCartData : function () {
                return $http.get(urlBase);
            },

            addToShoppingCart: function (imageId) {
                return $http({
                    url: urlBase + 'purchases',
                    method: "POST",
                    params: {
                        imageId: imageId
                    }
                });
            },

            removeFromShoppingCart: function (imageId) {
                return $http.delete(urlBase + "/purchases/" + imageId);
            }
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