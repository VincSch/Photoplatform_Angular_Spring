angular.module('photoplatform')
    .controller('imageComponentModalCtrl', ['$scope', '$modalInstance', 'imageData', 'ShoppingListService', function ($scope, $modalInstance, imageData, ShoppingListService) {

        var copyImageData = angular.copy(imageData);
        var exif = '{"Metadata":' + copyImageData.metadata + '}';
        copyImageData.metadata = JSON.parse(exif).Metadata;
        $scope.image = copyImageData;
        console.log(copyImageData);

        $scope.close = function () {
            console.log('Closing imageModal');
            $modalInstance.dismiss('close modal');
        };

        /*
        $scope.addToCart = function (imageId) {
            ShoppingListService.addToShoppingCart(imageId).success(function (data) {
                $rootScope.user.totalItems = data.totalItems;
                $scope.close();
            }).error(function(data){
                alert(data.message);
            });
        }
        */
    }])

    .directive('imagecomponent', ['$rootScope', '$modal', '$location', 'ShoppingListService', function ($rootScope, $modal, $location, ShoppingListService) {
        console.log($location);

        return {
            restrict: 'A',
            templateUrl: '/views/partials/profile/photographer/imageComponentX.html',
            scope: {
                image: '=imagecomponent'
            },
            link: function (scope, elem, attrs) {

                //scope.image = scope.customerimage;

                //Hide Collection and Showcase Link if CollectionView
                scope.searchResultView = (attrs.collectionview != "true");
                //console.log(scope.searchResultView);

                scope.openModal = function (imageData) {
                    $modal.open({
                        templateUrl: '/views/partials/profile/photographer/imageComponentX.mdl.html',
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


                /*
                scope.goToPhotograph = function (requestUserId) {
                    $location.path("view/showcase/" + requestUserId);
                };

                scope.goToCollection = function (collectionId) {
                    $location.path("collection/" + collectionId);
                };

                scope.addToCart = function (imageId) {
                    ShoppingListService.addToShoppingCart(imageId).success(function (data) {
                        $rootScope.user.totalItems = data.totalItems;
                    }).error(function (data) {
                        alert(data.message);
                    });
                }
                */
            }
        }
    }])
;