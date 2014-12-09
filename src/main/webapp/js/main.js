/*
 Slidemenu
 */
(function () {
    var body = document.body
        , menu_trigger = body.getElementsByClassName('menu-trigger')[0];

    if (typeof menu_trigger !== 'undefined') {
        menu_trigger.addEventListener('click', function () {
            body.className = ( body.className == 'menu-active' ) ? '' : 'menu-active';
        });
    }

}).call(this);

function fadeOutMenu() {
    $('body').removeClass('menu-active');
    return false;
}

function removePreviewImage(element) {
    $('input[type="file"]')[0].files = null;
    //var files = $('input[type="file"]')[0].files;
    //var img = $(element).prev();
    //for(var i=0;files.length>i;i++){
    //    if (files[i].name === img.attr('alt')) {
    //        files.splice(i, 1);
    //    }
    //}
    if (true || files.length)
        $(".upload").prop('disabled', true);
    $('#preview').html("");
    $('#preview').hide();
}