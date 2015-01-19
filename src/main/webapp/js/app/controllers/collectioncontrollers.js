photoplatformControllers.controller('CollectionCtrl',
    ['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', '$modal', 'UserService', 'ImageService', 'CollectionService',
        function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, $modal, UserService, ImageService, CollectionService) {
            var user = $cookieStore.get('user');
            //if user is not logged in or authorized redirect to login page
            if (undefined == user || !$rootScope.isLoggedIn()) {
                $location.path("/login");
                return;
            }

            $scope.collectionId = $routeParams.collectionId;

            var start = 0;
            var count = 100;

            // Photographer collection
            $scope.images = [];

            $scope.copy = angular.copy;

			/**
			 * Get collections data
			 */
			 $scope.getCollectionsData = function() {
			 	CollectionService.getCollection($scope.collectionId).success(function (data) {
                    $scope.collectionName = data.name;
                    $scope.collectionDescription = data.description;
                    $scope.images = data.images;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
			 };

            /**
             * Get all collections images.
             */
            $scope.getCollectionsImages = function () {
                CollectionService.getCollectionsImages($scope.collectionId, start, count).success(function (images) {
                    $scope.images = images;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
            };

            /**
             * Delete one image by image id.
             */
            $scope.deleteImage = function (image, index) {
                CollectionService.deleteImage($scope.collectionId, image.id).success(function (message) {
                    $scope.images.splice(index, 1);
                    $rootScope.success = message;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

            /**
             * Update one image by image id.
             */
            $scope.updateImage = function (editImage, originImage, status) {
                //replace comma by dot.
                editImage.price = ("" + editImage.price).replace(",", ".");
                ImageService.updateImage(editImage).success(function (image) {
                    angular.extend(originImage, image);
                    status.editing = false;
                    $rootScope.success = image.messageSuccess;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

            $scope.imagesWithoutCollection = [];

            $scope.open = function (size) {

                var modalInstance = $modal.open({
                    templateUrl: '/views/partials/profile/photographer/collection/imagesModal.html',
                    controller: 'ImagesModalCtrl',
                    size: size,
                    resolve: {
                        collectionId: function () {
                            return $scope.collectionId;
                        }
                    }
                });

                modalInstance.result.then(function (imagesToAdd) {
                    for (var i = 0; i < imagesToAdd.length; i++) {
                        var imageToAdd = imagesToAdd[i];
                        $scope.images.push(imageToAdd);
                    }

                }, function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
            };

            /**
             * Set image as titelimage for collection
             */
            $scope.setAsCollectionThumb = function (image) {
                CollectionService.setAsCollectionThumb($scope.collectionId, image.id).success(function (message) {
                    $rootScope.success = message;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                })
            };

            /**
            *
            */
            $scope.openModal = function (imageData) {
                console.log("Open");
                $modal.open({
                    templateUrl: '/views/partials/profile/photographer/imageComponent.mdl.html',
                    controller: 'imageComponentModalCtrl',
                    windowClass: 'imageComponentModal',
                    size: 'lg',
                    resolve: {
                        imageData: function () {
                            return imageData
                        }
                    }
                });
            };

        }]);


/**
 * Get specific public collection
 */
photoplatformControllers.controller('ViewCollectionCtrl',
	['$scope', '$rootScope', '$location', '$http', '$cookieStore', '$route', '$rootScope', '$routeParams', '$modal', 'UserService', 'ImageService', 'CollectionService',
		function ($scope, $rootScope, $location, $http, $cookieStore, $route, $rootScope, $routeParams, $modal, UserService, ImageService, CollectionService) {

            $scope.collectionId = $routeParams.collectionId;

            // Photographer collection
            $scope.images = [];
            $scope.show = false;
			/**
			 * Get collections data
			 */
			 $scope.getCollectionsData = function() {
			 	CollectionService.getCollection($scope.collectionId).success(function (data) {
			 	console.log(data);
                    $scope.collectionName = data.name;
                    $scope.photographer = data.userdata;
                    $scope.collectionDescription = data.description;
                    $scope.images = data.images;
           			$scope.show = true;
                }).error(function (data) {
                    $scope.log(data.errors);
                    $scope.errors = data.errors;
                });
			 };
    	}
	]
);


/**
 * Modal window controller to add image to collection.
 */
photoplatformControllers.controller('ImagesModalCtrl', function ($scope, $rootScope, $modalInstance, ImageService, CollectionService, collectionId) {

    $scope.imagesWithoutCollection = [];

    ImageService.getPhotographImagesWithoutCollection().success(function (imagesWithoutCollection) {
        $scope.imagesWithoutCollection = imagesWithoutCollection;
    }).error(function (data) {
        $scope.log(data.errors);
        $scope.errors = data.errors;
    });

    $scope.ok = function () {
        var imageIds = [];
        var imagesToAdd = [];
        for (var i = 0; i < $scope.imagesWithoutCollection.length; i++) {
            var imageToAdd = $scope.imagesWithoutCollection[i];
            if (imageToAdd.isAdded) {
                imageIds.push(imageToAdd.id);
                imagesToAdd.push(imageToAdd);
            }
        }

        CollectionService.addImages(collectionId, imageIds).success(function (message) {
            $rootScope.success = message;
            $modalInstance.close(imagesToAdd);
        }).error(function (data) {
            $scope.log(data.errors);
            $scope.errors = data.errors;
            $modalInstance.dismiss('cancel');
        })
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});



/**
 * Get showcase from specified user
 */
photoplatformControllers.controller('ViewShowcaseCtrl', ['$scope', '$routeParams', '$rootScope', '$location', '$http', '$cookieStore', 'CollectionService', '$route',
    function ($scope, $routeParams, $rootScope, $location, $http, $cookieStore, CollectionService, $route) {

        $scope.userId = $routeParams.userId;

        /**
         * Get showcase from specified user
         */
        //$scope.getShowcaseFrom = function () {
            var start = 0;
            var count = 100;
            //var requestUserId = 3;
            CollectionService.getShowcaseFrom(start, count, $scope.userId).success(function (collections) {
                $scope.showcase = collections;
            });
        //};


    }
]);
