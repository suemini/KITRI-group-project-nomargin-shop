function addToCart() {
    if (confirm("장바구니에 담으시겠습니까?")) {
        // 확인 버튼을 눌렀을 때의 동작
        alert("장바구니에 담겼습니다.");
        window.location.href = "/form/itemList/cart";
    } else {
        // 취소 버튼을 눌렀을 때의 동작
        alert("취소되었습니다.");
    }
}