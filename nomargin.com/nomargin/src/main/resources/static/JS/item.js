// 가격 계산 함수
function calculatePrice() {
    // item 엔티티 클래스에서 가져온 stock 값
    const stock = parseInt(document.getElementById("amount").max);/* item.stock 값 가져오는 코드 */

    // 입력된 수량 가져오기
    const amountInput = document.getElementById("amount");
    let amount = parseInt(amountInput.value) || 0;

    if (isNaN(amount) || amount < 1) {
        amountInput.value = 1;
        amount = 1;
    } else if (amount > stock) {
        alert("재고 수량을 초과하였습니다.");
        amountInput.value = 1;
        amount = 1;
    }

    const price = parseInt(document.getElementById("price").value.replace(",", ""));
    const totalPrice = amount * price;
    document.querySelector(".totalPrice").textContent = totalPrice.toLocaleString();
}


function setQuantity() {
    var amount = document.getElementById("amount").value;
    document.getElementById("quantity").value = amount;
}
