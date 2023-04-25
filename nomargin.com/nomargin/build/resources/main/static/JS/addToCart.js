function addToCart() {
    var itemCount = document.querySelectorAll('#order-box').length;
    if (itemCount === 0) {
        // 상품이 담겨져 있지 않은 경우
        alert("장바구니에 상품을 담아주세요.");
    } else {
        // 상품이 담겨져 있는 경우
        if (confirm("장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?")) {
            // 확인 버튼을 눌렀을 때의 동작
            window.location.href = "/cart";
        } else {
            // 취소 버튼을 눌렀을 때의 동작
            alert("취소되었습니다.");
        }
    }
}