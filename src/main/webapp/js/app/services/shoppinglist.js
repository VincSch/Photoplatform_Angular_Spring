/**
 * AuthService.
 * Login, register and passwordLost and passwordReset.
 */
angular.module('photoplatform').factory('ShoppingListService', ['$http',
    function ($http) {

        var urlBase = '/api/';

        return {

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