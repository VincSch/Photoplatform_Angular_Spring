/**
 * UserService.
 * Login, register and update user.
 */
angular.module('photoplatform').factory('UserService', ['$http',
    function ($http) {

        var urlBase = '/api/user';
        var urlBaseList = '/api/users';

        var userService = {};

        userService.login = function (email, pw) {
            paramData = {
                email: email,
                password: pw
            };

            return $http.post(urlBase + '/login', paramData);
        };

        userService.register = function (user) {
            return $http.post(urlBase + '/register', user);
        };

        userService.becomePhotographer= function (user) {
            return $http.post(urlBase + '/becomePhotographer', user);
        };

        userService.update = function (user) {
            return $http.post(urlBase + '/update', user);
        };

        userService.updatePhotographer = function (user) {
            return $http.post(urlBase + '/updatePhotographer', user);
        };

        userService.getUsers = function (name) {
            return $http.get(urlBase + '/byname/' + name);
        };

        userService.getEnabledUsers = function (start, count) {
            return $http.get(urlBaseList + '/admin/' + start + "/" + count);
        };

        userService.getUserProfileData = function (userId) {
            return $http.get(urlBaseList + '/profile/' + userId);
        };

        userService.getBecomePhotographers = function () {
            return $http.get(urlBaseList + '/becomephotographers');
        };

        userService.enablePhotographer = function (id) {
            return $http.post(urlBase + '/enablephotographer', {userId : id});
        };

        userService.makeAdmin = function (id) {
            return $http.get(urlBase + '/makeadmin/' + id);
        };

        userService.lockUser = function (id) {
            return $http.get(urlBase + '/lock/' + id);
        };

        userService.unlockUser = function (id) {
            return $http.get(urlBase + '/unlock/' + id);
        };

        userService.updateUserProfileData = function (userProfileData) {
            return $http.post(urlBaseList + '/update/', userProfileData);
        };

        return userService;
    }]);
