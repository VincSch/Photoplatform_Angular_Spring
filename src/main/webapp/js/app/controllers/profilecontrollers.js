photoplatformControllers.controller('ProfileCtrl', ['$scope', '$routeParams', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
    function ($scope, $routeParams, $rootScope, $location, $http, $cookieStore, UserService, $route) {
        //if user is not logged in or authorized redirect to login page
        var user = $cookieStore.get('user');
        if (undefined == user || !$rootScope.isLoggedIn()) {
            $location.path("/login");
            return;
        }

        $scope.userId = $routeParams.userId;

        if ($scope.userId == undefined) {
            $scope.userId = user.id;
        }

        UserService.getUserProfileData($scope.userId).success(function (responseUserProfileData) {
            $scope.userProfileData = responseUserProfileData;
        }).error(function (error) {
            console.log(error);
            $rootScope.error(error);
        });

        //    $scope.profileProgress = function() {
//		var result = 0;
//		if ($rootScope.user !== undefined) {
//			if ($rootScope.user.brithday !== null)
//				result += 1;
//			if ($rootScope.user.about !== null)
//				result += 1;
//			if ($rootScope.user.city !== null)
//				result += 1;
//			if ($rootScope.user.username !== null)
//				result += 1;
//			if ($rootScope.user.email !== null)
//				result += 1;
//		}
//		return result * 20;
//	};
    }]);

/*
 photoplatformControllers.controller('ProfileDetailCtrl', ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'UserService', '$route',
 function($scope, $rootScope, $location, $http, $cookieStore, UserService, $route) {
 $scope.today = function() {
 $scope.dt = new Date();
 };
 $scope.today();

 $scope.clear = function() {
 $scope.dt = null;
 };

 // Disable weekend selection
 $scope.disabled = function(date, mode) {
 return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6 ) );
 };

 $scope.toggleMin = function() {
 $scope.minDate = $scope.minDate ? null : new Date();
 };
 $scope.toggleMin();

 $scope.open = function($event) {
 $event.preventDefault();
 $event.stopPropagation();

 $scope.opened = true;
 };

 $scope.dateOptions = {
 formatYear : 'yy',
 startingDay : 1
 };

 $scope.initDate = new Date('2016-15-20');
 $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
 $scope.format = $scope.formats[2];

 $scope.updateUser = function(user) {
 //user.userRoles = null;
 delete user.authorities;
 delete user.accountNonExpired;
 delete user.credentialsNonExpired;
 UserService.update(user).success(function() {
 $rootScope.success = "Profil erfolgreich aktualisiert";
 }).error(function(error) {
 $rootScope.error = "Profil konnte nicht aktualisiert werden";
 });
 $location.path("/profile");
 $route.reload();
 };
 console.log($rootScope.user);
 }]);
 */
/*
 photoplatformControllers.controller('ProfileViewCtrl', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$cookieStore', 'UserService', '$route',
 function($scope, $rootScope, $routeParams, $location, $http, $cookieStore, UserService, $route) {
 UserService.getUserByName($routeParams.name).success(function(user) {
 $scope.profile = user;
 }).error(function(error) {
 $rootScope.error = "Nutzer " + user.username + " konnte nicht gefunden werden!";
 });

 UserService.getRecipeAmount($routeParams.name).success(function(amount) {
 $scope.recipeamount = amount;
 }).error(function(error) {
 $rootScope.error = "Nutzer " + $routeParams.name + " konnte nicht gefunden werden!";
 });

 UserService.getRecipeBookAmount($routeParams.name).success(function(amount) {
 $scope.recipebookamount = amount;
 }).error(function(error) {
 $rootScope.error = "Nutzer " + $routeParams.name + " konnte nicht gefunden werden!";
 });

 }]);
 */