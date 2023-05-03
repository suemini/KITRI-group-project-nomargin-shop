$(function () {
    $("#file1").change(function () {
        readURL1(this);
    });
});

function readURL1(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            //alert(e.target.result);
            $('#img1').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $("#file2").change(function () {
        readURL2(this);
    });
});

function readURL2(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            //alert(e.target.result);
            $('#img2').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $("#file3").change(function () {
        readURL1(this);
    });
});

function readURL3(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            //alert(e.target.result);
            $('#img3').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $("#file4").change(function () {
        readURL1(this);
    });
});

function readURL4(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            //alert(e.target.result);
            $('#img4').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $("#file5").change(function () {
        readURL1(this);
    });
});

function readURL5(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            //alert(e.target.result);
            $('#img5').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}