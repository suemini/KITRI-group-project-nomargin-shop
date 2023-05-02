function calculateTotal() {
    var quantities = document.getElementsByClassName("quantity");
    var prices = document.getElementsByClassName("price");
    var subtotals = document.getElementsByClassName("subtotal");
    var total = 0;

    for (var i = 0; i < quantities.length; i++) {
        var quantity = parseInt(quantities[i].value);
        var price = parseInt(prices[i].innerHTML.replace(/[^0-9]/g, ""));
        var subtotal = quantity * price;

        subtotals[i].innerHTML = numberWithCommas(subtotal) + " 원";
        total += subtotal;
    }

    document.getElementsByClassName("total-amount")[0].innerHTML = numberWithCommas(total) + " 원";
}

function cancel() {
    alert("주문을 취소했습니다.")
    return true
}

window.onload = calculateTotal;