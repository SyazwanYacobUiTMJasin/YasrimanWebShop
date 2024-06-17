// viewcart.js
document.addEventListener('DOMContentLoaded', function () {
  const cartItemsContainer = document.getElementById('cartItems');
  const cartTotal = document.getElementById('basket-total');
  const cartSubtotal = document.getElementById('basket-subtotal');
  const totalItems = document.querySelector('.total-items');
  const deliverySelection = document.querySelector('.summary-delivery-selection');
  const shippingFeeText = document.querySelector('.shippingfee');

  let cart = JSON.parse(localStorage.getItem('cart')) || [];
  let shippingFee = 0;

  function renderCart() {
      cartItemsContainer.innerHTML = '';
      let subtotal = 0;
      let totalItemCount = 0;

      cart.forEach(item => {
          const itemTotal = item.price * item.quantity;
          subtotal += itemTotal;
          totalItemCount += item.quantity;

          const productElement = document.createElement('div');
          productElement.classList.add('basket-product');
          productElement.innerHTML = `
              <div class="item">
                  <div class="product-image">
                      <img src="${item.imgSrc}" alt="${item.title}" class="product-frame" width="120" height="auto">
                  </div>
                  <div class="product-details">
                      <h1>${item.title}</h1>
                  </div>
              </div>
              <div class="price">${item.price.toFixed(2)}</div>
              <div class="quantity">
                  <input type="number" value="${item.quantity}" min="1" class="quantity-field">
              </div>
              <div class="subtotal">${itemTotal.toFixed(2)}</div>
              <div class="remove">
                  <button>Remove</button>
              </div>
          `;

          const quantityField = productElement.querySelector('.quantity-field');
          quantityField.addEventListener('change', function () {
              updateQuantity(item, quantityField.value);
          });

          const removeButton = productElement.querySelector('.remove button');
          removeButton.addEventListener('click', function () {
              removeItem(item);
          });

          cartItemsContainer.appendChild(productElement);
      });

      updateTotals(subtotal);
      totalItems.textContent = totalItemCount;
  }

  function updateTotals(subtotal) {
      const total = subtotal + shippingFee;
      cartSubtotal.textContent = `${subtotal.toFixed(2)}`;
      cartTotal.textContent = `${total.toFixed(2)}`;
  }

  function updateQuantity(item, newQuantity) {
      item.quantity = parseInt(newQuantity, 10);
      localStorage.setItem('cart', JSON.stringify(cart));
      renderCart();
  }

  function removeItem(item) {
      cart = cart.filter(cartItem => cartItem.title !== item.title);
      localStorage.setItem('cart', JSON.stringify(cart));
      renderCart();
  }

  function updateShippingFee() {
      const selectedOption = deliverySelection.value;
      switch (selectedOption) {
          case 'cod':
              shippingFee = 0;
              shippingFeeText.textContent = 'Shipping Fee: RM0.00';
              break;
          case 'poslaju':
              shippingFee = 15.0;
              shippingFeeText.textContent = 'Shipping Fee: RM15.00';
              break;
          case 'jnt':
              shippingFee = 10.0;
              shippingFeeText.textContent = 'Shipping Fee: RM10.00';
              break;
          case 'ninjavan':
              shippingFee = 12.0;
              shippingFeeText.textContent = 'Shipping Fee: RM12.00';
              break;
          default:
              shippingFee = 0;
              shippingFeeText.textContent = 'Shipping Fee: RM0.00';
              break;
      }
      renderCart();
  }

  deliverySelection.addEventListener('change', updateShippingFee);

  renderCart();
});

function goToPaymentPage() {
    const total = parseFloat(document.getElementById('basket-subtotal').textContent);
    const deliverySelection = document.querySelector('.summary-delivery-selection');
    const selectedOption = deliverySelection.value;

    if (total === 0) {
        alert("Your cart cannot be empty.");
    } else if (selectedOption === "0") {
        alert("Please select a delivery option.");
    } else {
        const basketSubtotal = document.getElementById('basket-subtotal').textContent;
        const basketTotal = document.getElementById('basket-total').textContent;
        const shippingFee = document.querySelector('.shippingfee').textContent.replace('Shipping Fee: RM', '');

        localStorage.setItem('subtotal', basketSubtotal);
        localStorage.setItem('total', basketTotal);
        localStorage.setItem('shipping', shippingFee);

        window.location.href = 'payment.html';
    }
  }