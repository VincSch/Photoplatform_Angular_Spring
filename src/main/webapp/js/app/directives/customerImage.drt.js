

angular.module('photoplatform')
    .controller('customerImageModalCtrl', ['$scope', '$modalInstance', 'imageData', function($scope, $modalInstance, imageData ){

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

    .directive('customerimage', ['$modal', function ($modal) {
        return {
            restrict: 'A',
            templateUrl: 'views/partials/profile/customerImage.drt.html',
            scope: {
                image: '=customerimage'
            },
            link: function (scope, elem, attrs) {

                //scope.image = scope.customerimage;

                scope.openModal = function(imageData){

//                    console.log( imageData );

                    $modal.open({
                      templateUrl: 'views/partials/profile/customerImage.mdl.html',
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
            }
        }
    }])
;