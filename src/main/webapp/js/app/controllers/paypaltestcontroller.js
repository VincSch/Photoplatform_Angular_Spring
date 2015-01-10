photoplatformControllers.controller('PaypalTestCtrl', 
	['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', '$modal', 'PaypalTestService', 
		function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, $modal, PaypalTestService) {
			
			$scope.OAuthToken = "---";
			
			$scope.getToken = function() {
			 	PaypalTestService.getToken().success(function (data) {
			 		console.log(data);
			 		$scope.OAuthToken = data; 
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
             },
			 
			 $scope.StartPurchase = function() {
			 	PaypalTestService.StartPurchase().success(function (data) {
			 		console.log(data);
			 		$scope.StartPurchaseResult = data; 
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
             },
                
			 $scope.ExecutePurchase = function() {
			 	PaypalTestService.ExecutePurchase($routeParams.paymentId, $routeParams.PayerID).success(function (data) {
			 		console.log(data);
			 		$scope.ExecutePurchaseResult = data; 
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
             }
   		}
   	]
);