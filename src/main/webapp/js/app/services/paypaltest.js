angular.module('photoplatform')
    .factory('PaypalTestService', ['$http', '$rootScope',
        function ($http, $rootScope) {
            var urlBase = '/api/paypaltest';

            var PaypalTestService = {};

			PaypalTestService.getToken = function() {
                return $http.get(urlBase + '/token');
			};
			

            return PaypalTestService;
        }]);
