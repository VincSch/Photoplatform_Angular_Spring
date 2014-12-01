angular.module('photoplatform').factory('ImageService', ['$http',
    function ($http) {

        var urlBase = '/api/image';
        var urlBaseList = '/api/images';

        var imageService = {};

        imageService.upload = function (postParam) {
            return $http.post(urlBase + '/upload', postParam);
        };

        return imageService;
    }]);
