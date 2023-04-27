
function calculateTotal() {
    let checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    let total = 0;
    checkboxes.forEach(function (checkbox) {
        let row = checkbox.parentNode.parentNode;
        let price = parseFloat(row.querySelector('.price').textContent.replace(',', ''));
        let quantity = parseInt(row.querySelector('.quantity').value);
        let subtotal = price * quantity;
        row.querySelector('.subtotal').textContent = subtotal.toLocaleString('en') + ' 원';
        total += price * quantity;
    });
    document.querySelector('.total').textContent = 'Total: ' + total.toLocaleString('en') + ' 원';
}

function purchase() {
    var selectedItems = [];
    var checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
    checkboxes.forEach(function(checkbox) {
        selectedItems.push(checkbox.parentNode.parentNode.querySelector('td:nth-child(2)').textContent);
    });
    if (selectedItems.length === 0) {
        alert("Please select at least one item.");
        return;
    }
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '/cart/purchase';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'cartItemIds';
    input.value = selectedItems.join(',');
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}