

angular.module('photoplatform')
    .controller('customerImageModalCtrl', ['$scope', '$modalInstance', 'imageData', function($scope, $modalInstance, imageData ){

        var copyImageData = angular.copy(imageData);
        var exif = '{"Metadata":' + copyImageData.metadata + '}';
        copyImageData.metadata = JSON.parse(exif).Metadata;
        $scope.image = copyImageData;
        console.log(copyImageData);

        $scope.close = function(){
            console.log('Closing imageModal');
            $modalInstance.dismiss('close modal');
        }

        $scope.addToCart = function(image){
            console.log(image);
        }

    }])

    .directive('customerimage', ['$modal', '$location', function ($modal, $location) {
    	console.log($location);
    	
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

                scope.openModal = function(imageData){

//                    console.log( imageData );

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
                }

                scope.goToPhotograph = function (requestUserId) {
                    $location.path("view/showcase/" + requestUserId);
                };

                scope.goToCollection = function (collectionId) {
                    $location.path("collection/" + collectionId);
                };
            }
        }
    }])
;