function order() {

    let select  = document.querySelector("select");
    let value = (select.options[select.selectedIndex].value);

    if (! value.includes("선택")) {
        document.getElementById("item-totalPrice-info").innerHTML +=
            "<div id='order-box'>" +
            "<input type='button' value='X' onclick='del()'>" +
            "<div class='order-box-list'>" +
            "<div class='order-item-name'>상품명</div>" +
            "<div class='order-item-size'>사이즈</div>" +
            "<div class='order-item-quantity'>수량</div>" +
            "<div class='order-item-price' >금액</div>" +
            "</div>";
    }
};

function del() {
    let deleteOrder = document.querySelector("#order-box");
    deleteOrder.remove();
}