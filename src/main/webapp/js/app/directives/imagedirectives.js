angular.module('photoplatform')
    .directive('fileInput', ['$rootScope', '$parse', function ($rootScope, $parse) {
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
                    //Calls event Handler down there for processing               
                    elem[0].files = files; 
                }
                var dropElem = $('#darg-and-drop-area');
                if (window.File && window.FileList && window.FileReader) {
                    dropElem.bind("dragover", FileDragHover);
                    dropElem.bind("dragleave", FileDragHover);
                    dropElem.bind("drop", FileSelectHandler);
                } else {
                    dropElem.css('display', 'none');
                }

                elem.bind('change', function () {
                	var NewFiles = elem[0].files;
                	
                	for (var i = 0; i < NewFiles.length; i++) {
						var File = NewFiles.item(i);
						
						if(File.type != "image/jpeg") {
							$rootScope.error = "Es werden nur jpeg-Formate unterstützt!";
							continue;
						}
						
						File.hash = File.name.slice(0, -4).replace(/[\.\(\)\{\}\\ _#:]/g, '');
						var reader = new FileReader();
                		reader.hash = File.hash;
               		 	reader.onload = function (e) {
                			$('#' + this.hash).attr('src', e.target.result);
                		}
                
						reader.readAsDataURL(File);
                
						$('#preview').show();
                		$('#bottom-upload').show();
                		$('.upload').prop('enabled', true);
                		$('.upload').prop('disabled', false);
                
						scope.files.push(File);		
					}
					
                    scope.$apply();
                });
            }
        }
    }])
;