// function addToCart() {
//     var itemCount = document.querySelectorAll('#order-box').length;
//     if (itemCount === 0) {
//         // 상품이 담겨져 있지 않은 경우
//         alert("장바구니에 상품을 담아주세요.");
//     } else {
//         // 상품이 담겨져 있는 경우
//         if (confirm("장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?")) {
//             // 확인 버튼을 눌렀을 때의 동작
//             document.getElementById("cart-form").submit();
//         } else {
//             // 취소 버튼을 눌렀을 때의 동작
//             alert("취소되었습니다.");
//         }
//     }
// }

function addToCart() {
    var itemCount = document.querySelectorAll('#order-box').length;
    var itemInfo = document.getElementById("name").value + "/" + document.getElementById("price").value + "/" + document.getElementById("store").value;
    var quantity = document.getElementById("amount").value;

    var cartForm = document.getElementById("cart-form");
    var itemInfoInput = document.createElement("input");
    itemInfoInput.setAttribute("type", "hidden");
    itemInfoInput.setAttribute("name", "itemInfo_" + itemCount);
    itemInfoInput.setAttribute("value", itemInfo);
    cartForm.appendChild(itemInfoInput);

    var quantityInput = document.createElement("input");
    quantityInput.setAttribute("type", "hidden");
    quantityInput.setAttribute("name", "quantity_" + itemCount);
    quantityInput.setAttribute("value", quantity);
    cartForm.appendChild(quantityInput);

    itemCount += parseInt(quantity);

    if (confirm("장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠습니까?")) {
        document.getElementById("cart-form").submit();
    } else {
        alert("취소되었습니다.");
    }
}