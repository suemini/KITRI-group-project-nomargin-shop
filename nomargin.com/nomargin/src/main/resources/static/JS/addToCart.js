function addToCart() {
    var quantity = document.getElementById("amount").value;
    if (quantity == 0) {
        alert("수량은 0개일 수 없습니다.");
    } else {
        if (confirm("장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠습니까?")) {
            document.getElementById("cart-form").submit();
        } else {
            alert("취소되었습니다.");
        }
    }
}