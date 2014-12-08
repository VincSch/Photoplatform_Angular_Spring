angular.module('photoplatform')
    .factory('CollectionService', ['$http', '$rootScope',
        function ($http, $rootScope) {
            var urlBase = '/api/collections';

            var collectionService = {};
//            imageService.upload = function (postParam, user) {
//                var fd = new FormData();
//                angular.forEach(postParam, function (file) {
//                    fd.append('files', file);
//                });
//                return $http.post(urlBase + '/upload', fd,
//                    {
//                        transformRequest: angular.identity,
//                        headers: {
//                            'Content-Type': undefined,
//                            'x-auth-token': user.secToken
//                        }
//                    });
//            };

            /**
             * Return all images.
             * @param start.
             * @param count.
             * @returns {HttpPromise}
             */
            collectionService.getCollectionsImages = function (collectionId, start, count) {
                return $http.get(urlBase + '/' + collectionId + '/images');
            };


            return collectionService;
        }]);
