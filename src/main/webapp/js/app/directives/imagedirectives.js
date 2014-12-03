angular.module('photoplatform')
    .directive('fileInput', ['$parse', function ($parse) {
        console.log("use image directive");
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {
                var FileDragHover = function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    //e.target.className = (e.type == "dragover" ? "hover" : "");
                }
                var FileSelectHandler = function (e) {
                    e = e.originalEvent;
                    FileDragHover(e);
                    // cancel event and hover styling
                    FileDragHover(e);
                    // fetch FileList object
                    var files = e.target.files || e.dataTransfer.files;
                    // process all File objects
                    elem[0].files = files;
                }
                if (window.File && window.FileList && window.FileReader) {
                    var dropElem = $('#darg-and-drop-area');
                    dropElem.bind("dragover", FileDragHover);
                    dropElem.bind("dragleave", FileDragHover);
                    dropElem.bind("drop", FileSelectHandler);
                    dropElem.css('display', 'block');
                } else {
                    dropElem.css('display', 'none');
                }

                elem.bind('change', function () {
                    $parse(attrs.fileInput)
                        .assign(scope, elem[0].files);
                    angular.forEach(elem[0].files, function (file) {
                        var hash = file.name.slice(0, -4).replace(/\.| |_|:/g, '');
                        $('#preview').append('<img id="' + hash + '" src="#" alt="' + file.name + '" class="preview-img" />');
                        var reader = new FileReader();
                        reader.hash = hash;
                        reader.onload = function (e) {
                            $('#' + this.hash).attr('src', e.target.result);
                        }
                        reader.readAsDataURL(file);
                    });
                    scope.$apply();
                });
            }
        }
    }])
;