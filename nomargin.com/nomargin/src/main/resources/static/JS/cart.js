function calculateTotal() {
    let checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    let total = 0;
    checkboxes.forEach(function (checkbox) {
        let row = checkbox.parentNode.parentNode;
        let price = parseFloat(row.querySelector('.price').textContent);
        let quantity = parseInt(row.querySelector('.quantity').value);
        let subtotal = price * quantity;
        row.querySelector('.subtotal').textContent = subtotal;
        total += price * quantity;
    });
    document.querySelector('.total').textContent = 'Total: ' + total;
}