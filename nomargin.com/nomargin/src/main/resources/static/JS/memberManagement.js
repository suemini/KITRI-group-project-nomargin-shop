function sample6_execDaumPostcode(index) {
    new daum.Postcode({
        oncomplete: function (data) {
            var address = data.zonecode + ' ' + data.address ;
            document.getElementById("sample6_address_" + index).value = address;
        }
    }).open();
}