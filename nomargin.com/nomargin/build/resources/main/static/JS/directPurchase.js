function directPurchase() {
    var quantity = document.getElementById("amount").value;
    if (quantity == 0) {
        alert("수량은 0개일 수 없습니다.");
    } else {
        if (confirm("주문페이지로 이동하시겠습니까?")) {
            document.getElementById("isGoingCart").value = 2; // 장바구니 페이지 안보여줌
            document.getElementById("cart-form").submit(); // 장바구니에 상품을 추가하고 페이지 이동
            // window.location.href = "/cart" + "/" + itemId;
        } else {
            // 취소 버튼을 누르면 그대로 그대로 현재 페이지.
            alert("취소되었습니다.");
        }
    }
}