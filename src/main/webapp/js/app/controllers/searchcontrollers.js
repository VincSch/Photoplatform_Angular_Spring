/**
 * Image search controller.
 */
photoplatformControllers.controller('ImageSearchCtrl', ['$scope', '$rootScope', 'ImageService', '$location',
    function ($scope, $rootScope, ImageService, $location) {

        /**
         * Search images one image by image id.
         */
        $scope.searchData = null;
        $scope.foundedMsg = null;
        $scope.showSearchResult = false;
        $scope.showClearButton = false;

        $scope.searchImage = function (value) {
            ImageService.searchImages(value).success(function (foundedImages) {
                $scope.foundedImagesPerPage = 8;
                $scope.foundedCurrentPage = 1;
                $scope.foundedImages = foundedImages;
                $scope.foundedImagesLength = foundedImages.length;
                $scope.showSearchResult = true;
                $scope.showSearchPagination = $scope.foundedImagesLength > $scope.imagesPerPage;
                if (foundedImages.length == 1) {
                    $scope.foundedMsg = foundedImages.length + " Bild gefunden!"
                } else {
                    $scope.foundedMsg = foundedImages.length + " Bilder gefunden!"
                }
                $scope.showClearButton = true;
                // Set watch on pagination numbers
                $scope.$watch('foundedCurrentPage + foundedImagesPerPage', function () {
                    var begin = (($scope.foundedCurrentPage - 1) * $scope.foundedImagesPerPage);
                    var end = begin + $scope.foundedImagesPerPage;
                    $scope.foundedFilterImages = $scope.foundedImages.slice(begin, end);
                });
            }).error(function (data) {
                $scope.log(data.errors);
                $scope.errors = data.errors;
            })
        };


        $scope.clear = function () {
            //clear array.
            while ($scope.foundedImages.length > 0) {
                $scope.foundedImages.pop();
            }
            $scope.showSearchResult = false;
            $scope.showClearButton = false;
            $scope.foundedMsg = null;
            $scope.searchData = null;
        };

        $scope.images = [];
        $scope.searchAllImages = function () {
            ImageService.searchImages("*").success(function (foundedImages) {
                $scope.images = foundedImages;
            }).error(function (data) {
                $scope.errors = data.errors;
                $scope.log(data.errors);
            })
        };

        $scope.imagesPerPage = 8;
        $scope.initRecomendationContent = function () {
            $scope.recomendationCurrentPage = 1;

            ImageService.searchImages("*").success(function (foundedImages) {
                $scope.recomendationImages = foundedImages;
                $scope.recomendationImagesLength = foundedImages.length;

                // Set watch on pagination numbers
                $scope.$watch('recomendationCurrentPage + imagesPerPage', function () {
                    var begin = (($scope.recomendationCurrentPage - 1) * $scope.imagesPerPage);
                    var end = begin + $scope.imagesPerPage;
                    $scope.recomendationFilteredImages = $scope.recomendationImages.slice(begin, end);
                });
            }).error(function (data) {
                $scope.errors = data.errors;
                $scope.log(data.errors);
            })
        };

        /**
         * Backup code for carousel view.
         * The html-code is comment out!
         * @type {Array}
         */
        /*
         $scope.recomendationSlides = new Array();
         $scope.initRecomendationCarousel = function () {
         var slideSize = 1;
         if (screen.width > 1200) {
         slideSize = 5;
         } else {
         if (screen.width > 500) {
         slideSize = 3;
         }
         }

         ImageService.searchImages("*").success(function (foundedImages) {
         $scope.images = foundedImages;

         var i, j;
         for (i = 0, j = $scope.images.length; i < j; i += slideSize) {
         var slide = $scope.images.slice(i, i + slideSize);
         // do whatever
         $scope.recomendationSlides.push(slide);
         }
         }).error(function (data) {
         $scope.errors = data.errors;
         $scope.log(data.errors);
         })
         };
         */
    }]);