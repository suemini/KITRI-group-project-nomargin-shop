function addToCart() {
    var quantity = document.getElementById("amount").value;
    if (quantity == 0) {
        alert("수량은 0개일 수 없습니다.");
    } else {
        if (confirm("장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠습니까?")) {
            document.getElementById("cart-form").submit(); // 장바구니에 상품을 추가하고 페이지 이동
            setTimeout(function() {
                window.location.href = "/cart";
            }, 1000); // 1초 후에 페이지 이동
        } else {
            // 취소 버튼을 누르면 그대로 장바구니에 추가됩니다.
            document.getElementById("cart-form").submit();
            alert("취소되었습니다.");
        }
    }
}