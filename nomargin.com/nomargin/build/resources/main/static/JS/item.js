// function order() {
//     let select = document.querySelector("select");
//     let value = (select.options[select.selectedIndex].value);
//
//     const itemName = document.getElementById('name').value;
//     const itemSize = select.options[select.selectedIndex].value;
//     const itemCount = document.getElementById('count').value;
//     const itemPrice = document.getElementById('price').value;
//
//     if (!value.includes("선택")) {
//         let totalPrice = parseInt(itemPrice); // totalPrice 변수를 itemPrice로 초기화
//         if (itemCount > 1) {
//             totalPrice *= parseInt(itemCount); // 개수에 맞게 곱셈 수행
//         }
//         document.getElementById("item-totalPrice-info").innerHTML +=
//             "<div id='order-box'>" +
//             "<div id='order-cancel-button'>" +
//             "<input type='button' value='X' onclick='del()'>" +
//             "</div>" +
//             "<div id='show-ItemList'>" +
//             "<input type='text' id='name' name='name' class='form-itemName' value='" + itemName + "' readonly>" +
//             "<input type='text' id='item-size' name='item-size' class='form-itemSize' value='" + itemSize + "' readonly>" +
//             "<input type='text' id='item-count' name='item-count' class='form-itemCount' value='" + itemCount + "' readonly>" +
//             "<input type='text' id='item-price' name='item-price' class='form-itemPrice' value='" + totalPrice + "' readonly>" +
//             "</div>" +
//             "</div>" ;
//         updateTotalPrice(totalPrice);
//     }
// }
//
//
// function del() {
//     let deleteOrder = document.querySelector("#order-box");
//     let priceElem = deleteOrder.querySelector(".form-itemPrice");
//     let price = parseInt(priceElem.value);
//     deleteOrder.remove();
//     updateTotalPrice(-price);
// }
//
// function updateTotalPrice(price) {
//     // 총 결제 금액 업데이트 함수
//     let totalPriceElem = document.querySelector('.totalPrice');
//     let totalPrice = parseInt(totalPriceElem.textContent);
//     totalPrice = totalPrice + price;
//     totalPriceElem.textContent = totalPrice;
// }
//
// function displayTotalPrice() {
//     let totalPayment = 0;
//     let itemPriceElems = document.getElementsByClassName('form-itemPrice');
//
//     if (itemPriceElems.length === 0) {
//         let singleItemPrice = parseInt(document.getElementById('item-price').value);
//         totalPayment = singleItemPrice;
//     } else {
//         for (let i = 0; i < itemPriceElems.length; i++) {
//             if (itemPriceElems[i].parentNode.parentNode.id === "item-totalPrice-info") {
//                 totalPayment += parseInt(itemPriceElems[i].value);
//             }
//         }
//     }
//     document.querySelector(".totalPrice").value = totalPayment;
// }

// function order() {
//     const itemName = document.getElementById('name').value;
//     const itemPrice = document.getElementById('price').value;
//     const itemCount = document.getElementById('amount').value;
//
//     let totalPrice = parseInt(itemPrice);
//     if (itemCount > 1) {
//         totalPrice *= parseInt(itemCount);
//     }
//
//     document.getElementById("item-totalPrice-info").innerHTML +=
//         "<div id='order-box'>" +
//         "<div id='order-cancel-button'>" +
//         "<input type='button' value='X' onclick='del()'>" +
//         "</div>" +
//         "<div id='show-ItemList'>" +
//         "<input type='text' id='name' name='name' class='form-itemName' value='" + itemName + "' readonly>" +
//         "<input type='text' id='item-count' name='item-count' class='form-itemCount' value='" + itemCount + "' readonly>" +
//         "<input type='text' id='item-price' name='item-price' class='form-itemPrice' value='" + totalPrice + "' readonly>" +
//         "</div>" +
//         "</div>";
//
//     updateTotalPrice(totalPrice);
// }
//
//
// function del() {
//     let deleteOrder = document.querySelector("#order-box");
//     let priceElem = deleteOrder.querySelector(".form-itemPrice");
//     let price = parseInt(priceElem.value);
//     deleteOrder.remove();
//     updateTotalPrice(-price);
// }
//
// function updateTotalPrice(price) {
//     let totalPriceElem = document.querySelector('.totalPrice');
//     let totalPrice = parseInt(totalPriceElem.textContent);
//     totalPrice = totalPrice + price;
//     totalPriceElem.textContent = totalPrice;
// }

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