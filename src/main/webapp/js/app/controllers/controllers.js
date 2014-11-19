var photoplatformControllers = angular.module('photoplatformControllers', []);

photoplatformControllers.controller('LoginCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
function($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

	$rootScope.login = function(username, password) {
		var hashedPw = MD5(password);
		UserService.login(username, hashedPw).success(function(user) {
			$rootScope.user = user;
			$http.defaults.headers.common[xAuthTokenHeaderName] = user.secToken;
			$cookieStore.put('user', user);
			console.log(user);
			if($rootScope.isCustomer())
				$location.path("/profile");
			if($rootScope.isPhotographer())
				$location.path("/profile");
			if($rootScope.isAdmin())
				$location.path("/admin");
		}).error(function(error) {
		});
	};
}]);

photoplatformControllers.controller('RegisterCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
function($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {

	$rootScope.register = function(username, password, email) {
		var hashedPw = MD5(password);
		var user = {'username' : username, 'password' : hashedPw, 'email' : email};
		UserService.register(user).success(function() {
			$location.path("/login");
			$rootScope.success = "Du hast dich erfolgreich registriert. Du kannst dich nun mit deinem Benutzernamen anmelden!";
			$rootScope.transferSuccess = true;
		}).error(function(error) {
		});
	};
}]);

photoplatformControllers.controller('AdminMenuCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {
		var user = $cookieStore.get('user');
		//if user is not logged in or authrized redirect to login page
		if(undefined == user || !$rootScope.isLoggedIn() || !$rootScope.isAdmin() ){
			$location.path("/login");
			return;
		}else{
            $scope.currentPage = 1;
            $scope.numPerPage = 5;
			var start = 0;
			var count = 100;
			UserService.getUsers(start, count).success(function (users) {
                $scope.users = users;
                $scope.totalItems = users.length;
                $scope.showPagination = users.length > $scope.numPerPage;

                // Set watch on pagination numbers
                $scope.$watch('currentPage + numPerPage', function () {
                    var begin = (($scope.currentPage - 1) * $scope.numPerPage);
                    var end = begin + $scope.numPerPage;
                    $scope.filteredUsers = $scope.users.slice(begin, end);
                });
			}).error(function (error) {
				console.log(error);
			});

            $scope.pageChanged = function () {
                //check if max count was achieved, than load next 100 users.
                //not implemented yet.
            };
		}
    }]);