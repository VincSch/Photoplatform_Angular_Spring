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
			 };
   		}
   	]
);