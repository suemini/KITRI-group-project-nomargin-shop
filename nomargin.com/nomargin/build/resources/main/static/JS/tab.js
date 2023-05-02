$(document).ready(function () {
    $('a[href="#item-desc"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".item-subImages").offset().top
        }, 500);
    });

    $('a[href="#item-comments"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".item-comments").offset().top
        }, 500);
    });

    // $('a[href="#item-rating"]').click(function () {
    //     $('html, body').animate({
    //         scrollTop: $(".item-rating").offset().top
    //     }, 1000);
    // });

    $('a[href="#item-refund"]').click(function () {
        $('html, body').animate({
            scrollTop: $(".refund").offset().top
        }, 500);
    });
});