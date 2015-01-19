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
    }]);