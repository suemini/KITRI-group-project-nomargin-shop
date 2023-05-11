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

function validateFile(fileInput) {
    var allowedExtensions = ['jpg', 'jpeg', 'png'];
    var allowedMimeTypes = ['image/jpeg', 'image/png'];
    var maxSize = 10 * 1024 * 1024; // 10MB (허용할 파일 최대 크기)

    var file = fileInput.files[0];
    var fileName = file.name;
    var fileExtension = fileName.split('.').pop().toLowerCase();
    var fileType = file.type;
    var fileSize = file.size;

    // 파일 확장자 및 MIME 타입 검사
    if (!allowedExtensions.includes(fileExtension) || !allowedMimeTypes.includes(fileType)) {
        alert('올릴 수 없는 파일 양식입니다.');
        fileInput.value = ''; // 파일 입력 필드 초기화
    }

    // 파일 크기 검사
    if (fileSize > maxSize) {
        alert('파일의 크기는 최대 10MB까지 허용됩니다.');
        fileInput.value = ''; // 파일 입력 필드 초기화
    }
}