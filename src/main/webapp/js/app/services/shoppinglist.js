/**
 * AuthService.
 * Login, register and passwordLost and passwordReset.
 */
angular.module('photoplatform').factory('ShoppingListService', ['$http', '$rootScope',
    function ($http, $rootScope) {

        var urlBase = '/api/';

        return {
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