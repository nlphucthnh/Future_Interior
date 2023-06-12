
document.getElementById('quantity-minus').addEventListener('click', function() {
    var quantityInput = document.getElementById('quantity-input');
    var currentQuantity = parseInt(quantityInput.value);
    if (currentQuantity > 1) {
      quantityInput.value = currentQuantity - 1;
    }
  });
  
  document.getElementById('quantity-plus').addEventListener('click', function() {
    var quantityInput = document.getElementById('quantity-input');
    var currentQuantity = parseInt(quantityInput.value);
    quantityInput.value = currentQuantity + 1;
  });