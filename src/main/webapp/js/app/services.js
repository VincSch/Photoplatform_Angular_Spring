/**
 * UserService.
 * Login, register and update user.
 */
angular.module('photoplatform').factory('UserService', ['$http',
    function ($http) {

        var urlBase = '/api/user';
        var urlBaseList = '/api/users';

        var userService = {};

        userService.login = function (name, pw) {
            paramData = {
                username: name,
                password: pw
            };

            return $http.post(urlBase + '/login', paramData);
        };

        userService.register = function (user) {
            return $http.post(urlBase + '/register', user);
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

        userService.getDisabledUsersByRole = function (roleName) {
            return $http.get(urlBaseList + '/disabled/' + roleName);
        };

        userService.enablePhotograph = function (id) {
            return $http.get(urlBase + '/enablephotograph/' + id);
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

        return userService;
    }]);