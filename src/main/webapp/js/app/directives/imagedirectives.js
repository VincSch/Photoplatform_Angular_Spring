angular.module('photoplatform')
    .directive('fileInput', ['$parse', function ($parse) {
        console.log("use image directive");
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {
                elem.bind('change', function () {
                    $parse(attrs.fileInput)
                        .assign(scope, elem[0].files);
                    angular.forEach(elem[0].files, function (file) {
                        var hash = file.name.slice(0, -3).replace(/[.| |_]/,'');
                        $('#preview').append('<img id="' + hash + '" src="#" alt="' + file.name + '" />');
                        var reader = new FileReader();
                        reader.hash = hash;
                        reader.onload = function (e) {
                            $('#' + this.hash).attr('src', e.target.result);
                        }
                        reader.readAsDataURL(file);

                        //var j = 0, k = files.length;
                        //for (var i = 0; i < k; i++) {
                        //    var reader = new FileReader();
                        //    reader.onloadend = function (evt) {
                        //        if (evt.target.readyState == FileReader.DONE) {
                        //            data["File_Content" + j] = btoa(evt.target.result);
                        //            j++;
                        //            if (j == k){
                        //                alert('All files read');
                        //            }
                        //        }
                        //    };
                        //    reader.readAsBinaryString(files[i]);
                        //}
                    });
                    scope.$apply();
                });
            }
        }
    }]);