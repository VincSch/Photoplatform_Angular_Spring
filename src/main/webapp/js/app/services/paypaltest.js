angular.module('photoplatform')
    .factory('PaypalTestService', ['$http', '$rootScope',
        function ($http, $rootScope) {
            var urlBase = '/api/paypaltest';

            var PaypalTestService = {};

			PaypalTestService.getToken = function() {
                return $http.get(urlBase + '/token');
			};
			
			PaypalTestService.StartPurchase = function() {
                return $http.get(urlBase + '/purchase');
			};
			
			PaypalTestService.ExecutePurchase = function(paymentId, PayerID) {
                return $http.get(urlBase + '/purchase/approved', {params: {'paymentId': paymentId, 'PayerID': PayerID}});
			};
			

            return PaypalTestService;
        }]);
