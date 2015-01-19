angular.module('photoplatform')
    .controller('customerImageModalCtrl', ['$scope', '$modalInstance', 'imageData', 'ShoppingListService', function ($scope, $modalInstance, imageData, ShoppingListService) {

        var copyImageData = angular.copy(imageData);
        var exif = '{"Metadata":' + copyImageData.metadata + '}';
        copyImageData.metadata = JSON.parse(exif).Metadata;
        $scope.image = copyImageData;
        console.log(copyImageData);

        $scope.close = function () {
            console.log('Closing imageModal');
            $modalInstance.dismiss('close modal');
        };

        $scope.addToCart = function (imageId) {
            ShoppingListService.addToShoppingCart(imageId).success(function (data) {
                $rootScope.user.totalItems = data.totalItems;
                $scope.close();
            }).error(function(data){
                //alert(data.message);
            });
        }

    }])

    .directive('customerimage', ['$rootScope', '$modal', '$location', '$cookieStore', '$window', 'ShoppingListService', function ($rootScope, $modal, $location, $cookieStore, $window, ShoppingListService) {
        return {
            restrict: 'A',
            templateUrl: '/views/partials/profile/customerImage.html',
            scope: {
                image: '=customerimage'

            },
            link: function (scope, elem, attrs) {

                //scope.image = scope.customerimage;

                //Hide Collection and Showcase Link if CollectionView
                scope.searchResultView = (attrs.collectionview != "true");
                //console.log(scope.searchResultView);

                scope.openModal = function (imageData) {
                //check if user is logged in
                     var user = $cookieStore.get('user');
                        if (undefined == user || !$rootScope.isLoggedIn()) {
                            //$location.path("/login");
                            $rootScope.error = "Um Bilder vergrößert zu betrachten, logge dich bitte ein!"
                            $window.scrollTo(0, 0);
                            return;
                        }

                    $modal.open({
                        templateUrl: '/views/partials/profile/customerImage.mdl.html',
                        controller: 'customerImageModalCtrl',
                        windowClass: 'customerImageModal',
                        size: 'lg',
                        resolve: {
                            imageData: function () {
                                return imageData
                            }
                        }
                    });
                };

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
                        //alert(data.message);
                    });
                }
            }
        }
    }])
;