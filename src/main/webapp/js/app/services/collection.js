angular.module('photoplatform')
    .factory('CollectionService', ['$http', '$rootScope',
        function ($http, $rootScope) {
            var urlBase = '/api/collections';

            var collectionService = {};

            /**
             * Returns the collection data.
             * @param collectionId
             * @returns {HttpPromise}
             */
			collectionService.getCollection = function(collectionId) {
                return $http.get('/api/viewcollection/' + collectionId);
			};
			
            /**
             * Return all images.
             * @param collectionId
             * @param start.
             * @param count.
             * @returns {HttpPromise}
             */
            collectionService.getCollectionsImages = function (collectionId, start, count) {
                return $http.get(urlBase + '/' + collectionId + '/images');
            };

            /**
             * Delete image from collection
             * .
             * @param collectionId.
             * @param imageId.
             * @returns {HttpPromise}
             */
            collectionService.deleteImage = function (collectionId, imageId) {
                return $http.delete(urlBase + '/' + collectionId + '/images/' + imageId);
            };

            /**
             * Add images to collection.
             * @param collectionId.
             * @param imageIds.
             * @param imageId.
             * @returns {HttpPromise}
             */
            collectionService.addImages = function (collectionId, imageIds) {
                if (imageIds.length > 0) {
                    return $http({
                        method: 'PUT',
                        url: urlBase + '/' + collectionId + '/images',
                        params: {
                            imageIds: imageIds
                        }
                    });
                }
                return null;

            };

            collectionService.getShowcaseFrom = function (start, count, requestUserId) {
                return $http.get('/api/' + 'viewshowcase', {params: {'start': start, 'count': count, 'requestUserId': requestUserId }});
            };

            collectionService.setAsCollectionThumb = function (collectionId, imageId) {
                return $http({
                    method: 'PATCH',
                    url: urlBase + '/setAsThumbnail/' + collectionId + '/images/' + imageId,
                });
            };

            return collectionService;
        }]);
