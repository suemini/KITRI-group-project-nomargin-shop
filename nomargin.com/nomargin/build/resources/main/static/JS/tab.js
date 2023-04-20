$(document).ready(function () {
    $('a[href="#item-desc"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".item-subImages").offset().top
        }, 1000);
    });

    $('a[href="#item-comments"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".item-comments").offset().top
        }, 1000);
    });

    $('a[href="#item-rating"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".item-rating").offset().top
        }, 1000);
    });

    $('a[href="#item-seller"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".seller-info").offset().top
        }, 1000);
    });
});