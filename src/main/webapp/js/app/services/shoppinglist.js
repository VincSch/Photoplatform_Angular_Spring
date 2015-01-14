/**
 * AuthService.
 * Login, register and passwordLost and passwordReset.
 */
angular.module('photoplatform').factory('ShoppingListService', ['$http',
    function ($http) {

        var urlBase = '/api/';

        return {
            /**
             * Get image as bytes
             *
             * @param image id
             * @returns {HttpPromise}
             */
            getRawImage: function (imageId) {
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