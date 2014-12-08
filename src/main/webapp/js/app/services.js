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

        userService.becomePhotographer = function (user) {
            return $http.post(urlBase + '/becomePhotographer', user);
        };

        userService.update = function (user) {
            return $http.post(urlBase + '/update', user);
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
            return $http.post(urlBase + '/enablephotographer', {userId: id});
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

        userService.changePassword = function (passwordData) {
            return $http.post(urlBaseList + '/changepassword', passwordData);
        };

        return userService;
    }]);

/**
 * Photographer Service.
 * Login, register and update user.
 */
angular.module('photoplatform').factory('PhotographerService', ['$http',
    function ($http) {
        var urlBase = '/api/';

        return {

            /**
             * Update photographer
             *
             * @param photographer
             * @returns {HttpPromise}
             */
            updatePhotographer: function (photographer) {
                return $http.post(urlBase + '/updatePhotographer', photographer);
            },

            /**
             * Create new collection
             *
             * @param collection the collection
             * @returns {HttpPromise}
             */
            createCollection: function (collection) {
                return $http.post(urlBase + '/collections/create', collection);
            },

            /**
             * Update collection
             *
             * @param collection
             * @returns {HttpPromise}
             */
            updateCollection: function (collection) {
                // There is no .pathc method in $http...
                return $http({
                    method: 'PATCH',
                    url: urlBase + 'collections/photographers',
                    data: angular.toJson(collection)
                });
            },

            /**
             * Get collection
             *
             * @param name
             * @param description
             * @returns {HttpPromise}
             */
            getCollections: function (start, count) {
                //Sorry, i have replaced this endpoint.
                //return $http.get(urlBase + 'collections/photographers', {params: {'start': start, 'count': count}});

                //Should work, not tested!
                return $http.get(urlBase + 'collections/photographers/' + start + "/" + count);
            },

            /**
             * Create collection in showcase
             *
             * @param name
             * @param description
             * @returns {HttpPromise}
             */
            getShowcase: function (start, count) {
                return $http.get(urlBase + 'showcase', {params: {'start': start, 'count': count}});
            },

            /**
             * Update collection showcase
             *
             * @param collectionId
             * @param isPublic
             * @returns {HttpPromise}
             */
            updateCollectionShowcase: function (collectionId, isPublic) {
                return $http.post(urlBase + 'collections/showcase', {'id': collectionId, 'isPublic': isPublic});
            },

            /**
             * Delete collection
             * @param collectionId
             */
            deleteCollection: function (collectionId) {
                return $http.delete(urlBase + 'collections/' + collectionId);
            }
        };

    }]);
