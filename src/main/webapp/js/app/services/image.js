angular.module('photoplatform')
    .factory('ImageService', ['$http', '$rootScope',
        function ($http, $rootScope) {
            var urlBase = '/api/photographer';

            var imageService = {};
            imageService.upload = function (postParam, user) {
                var fd = new FormData();
                angular.forEach(postParam, function (file) {
                    fd.append('files', file);
                });
                return $http.post(urlBase + '/upload', fd,
                    {
                        transformRequest: angular.identity,
                        headers: {
                            'Content-Type': undefined,
                            'x-auth-token': user.secToken
                        }
                    });
            };

            /**
             * Return all images.
             * @param start.
             * @param count.
             * @returns {HttpPromise}
             */
            imageService.getAllImages = function (start, count) {
                return $http.get(urlBase + '/images');
            };

            /**
             * Return photograph images without collection.
             * @param start.
             * @param count.
             * @returns {HttpPromise}
             */
            imageService.getPhotographImagesWithoutCollection = function () {
                return $http.get(urlBase + '/images', {params: { isAdded: false }});
            };

            /**
             * Delete image by id.
             * @param start.
             * @returns {HttpPromise}
             */
            imageService.deleteImage = function (imageId) {
                return $http.delete(urlBase + '/images/' + imageId);
            };

            /**
             * Update image.
             *
             * @param data image data.
             * @returns {HttpPromise}
             */
            imageService.updateImage = function (image) {
                // There is no .pathc method in $http...
                return $http({
                    method: 'PATCH',
                    url: urlBase + '/images',
                    data: angular.toJson(image)
                });
            };

            /**
             * Search images.
             *
             * @param searchData
             * @returns {HttpPromise}
             */
            imageService.searchImages = function (searchData) {
                return $http.get('api/images/_search', {params: {'searchData': searchData}});
            };

            return imageService;
        }]);
