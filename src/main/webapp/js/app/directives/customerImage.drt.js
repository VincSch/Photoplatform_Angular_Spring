

angular.module('photoplatform')
    .controller('customerImageModalCtrl', ['$scope', '$modalInstance', 'imageData', function($scope, $modalInstance, imageData ){

        //dirty hotfix
        if(imageData.metadata == "test metadata")
        {
            imageData.metadata = "{}";
        }
        var exif = imageData.metadata;
        exif = '{"Metadata":' + exif + '}';
        imageData.metadata = JSON.parse(exif).Metadata;
        $scope.image = imageData;
        console.log( imageData );

        $scope.close = function(){
            console.log('Closing imageModal');
            $modalInstance.dismiss('close modal');
        }

        $scope.addToCart = function(image){
            console.log(image);
        }

    }])

    .directive('customerimage', ['$modal', '$location', function ($modal, $location) {
        return {
            restrict: 'A',
            templateUrl: '/views/partials/profile/customerImage.html',
            scope: {
                image: '=customerimage'
            },
            link: function (scope, elem, attrs) {

                //scope.image = scope.customerimage;

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