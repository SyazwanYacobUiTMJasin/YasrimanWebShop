document.addEventListener('DOMContentLoaded', function () {
    const cartItemsContainer = document.getElementById('cartItems');
    const cartDataInput = document.getElementById('cartData');
    const checkoutForm = document.getElementById('checkoutForm');
    
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

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
                    <input type="number" value="${item.quantity}" min="1" max="${item.maxStock}" class="quantity-field">
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
        cartDataInput.value = JSON.stringify(cart);
    }

    function updateTotals(subtotal) {
        const total = subtotal;
        document.getElementById('basket-subtotal').textContent = subtotal.toFixed(2);
        document.getElementById('basket-total').textContent = total.toFixed(2);
        document.querySelector('.total-items').textContent = cart.length;
    }

    function updateQuantity(item, newQuantity) {
        newQuantity = parseInt(newQuantity, 10);
    
        // Prevent negative or zero quantities
        if (newQuantity < 1) {
            alert("Quantity cannot be less than 1.");
            newQuantity = 1; // Reset to minimum quantity
        }
    
        if (newQuantity > item.maxStock) {
            alert(`Maximum stock (${item.maxStock}) reached for this item.`);
            newQuantity = item.maxStock;
        }
    
        item.quantity = newQuantity;
        localStorage.setItem('cart', JSON.stringify(cart));
        renderCart();
    }
    

    function removeItem(item) {
        cart = cart.filter(cartItem => cartItem.title !== item.title);
        localStorage.setItem('cart', JSON.stringify(cart));
        renderCart();
    }

    let shippingFee = 0;

    checkoutForm.addEventListener('submit', function (event) {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            const cartDataWithoutImages = cart.map(item => {
                const { imgSrc, ...itemWithoutImage } = item;
                return itemWithoutImage;
            });
            cartDataInput.value = JSON.stringify(cartDataWithoutImages);
            console.log('Sending cart data:', cartDataInput.value); // For debugging
        }
    });

    renderCart();
});

function validateForm() {
    const total = parseFloat(document.getElementById('basket-subtotal').textContent); 

    if (total === 0) {
        alert("Your cart cannot be empty.");
        return false;
    } else {
        return true;
    }
}
