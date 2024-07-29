document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const businessRows = document.querySelectorAll('.list-item');
    const noResults = document.getElementById('noResults');

    searchInput.addEventListener('keyup', function () {
        const filter = searchInput.value.toLowerCase();
        let hasVisibleItems = false;

        businessRows.forEach(function (item) {
            const ownerName = item.querySelector('.business-row strong').textContent.toLowerCase();

            if (ownerName.indexOf(filter) > -1) {
                item.style.display = '';
                hasVisibleItems = true;
            } else {
                item.style.display = 'none';
            }
        });

        if (hasVisibleItems) {
            noResults.style.display = 'none';
        } else {
            noResults.style.display = 'block';
        }
    });
});



document.addEventListener('DOMContentLoaded', function () {
    const query = localStorage.getItem('searchQuery') || '';
    const searchInput = document.getElementById('searchInput');
    const productContainer = document.getElementById('productContainer');
    const noResults = document.getElementById('noResults');
    searchInput.value = query;

    function searchProducts(query) {
        const items = productContainer.getElementsByClassName('grid-item');
        let found = false;

        for (let item of items) {
            const title = item.querySelector('h3').textContent.toLowerCase();
            if (title.includes(query.toLowerCase())) {
                item.style.display = '';
                found = true;
            } else {
                item.style.display = 'none';
            }
        }

        if (!found) {
            noResults.style.display = 'block';
        } else {
            noResults.style.display = 'none';
        }
    }

    searchInput.addEventListener('input', function () {
        const query = searchInput.value;
        localStorage.setItem('searchQuery', query);
        searchProducts(query);
    });

    searchProducts(query);
});

// product.js

document.addEventListener('DOMContentLoaded', function () {
    const addToCartButtons = document.querySelectorAll('.add-to-cart');

    addToCartButtons.forEach(button => {
        const gridItem = button.closest('.grid-item');
        const maxStock = parseInt(gridItem.querySelector('.stock').textContent.split(': ')[1]);
        
        if (maxStock === 0) {
            button.disabled = true;
            button.style.opacity = 0.5;
        } else {
            button.addEventListener('click', function () {
                const title = gridItem.querySelector('h3').textContent;
                const price = parseFloat(gridItem.querySelector('.price').textContent.replace('RM', ''));
                const imgSrc = gridItem.querySelector('img').src;
                const inventoryID = gridItem.querySelector('.inventoryID').value;

                addToCart({ title, price, imgSrc, inventoryID, maxStock });
                updateButtonState(button, inventoryID);
            });
        }
    });

    function addToCart(item) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const existingItem = cart.find(cartItem => cartItem.inventoryID === item.inventoryID);

        if (existingItem) {
            if (existingItem.quantity < item.maxStock) {
                existingItem.quantity += 1;
            } else {
                alert(`Maximum stock (${item.maxStock}) reached for this item.`);
                return;
            }
        } else {
            if (item.maxStock > 0) {
                item.quantity = 1;
                cart.push(item);
            } else {
                alert('This item is out of stock.');
                return;
            }
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        updateButtonState(null, item.inventoryID);
    }

    function updateButtonState(button, inventoryID) {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const cartItem = cart.find(item => item.inventoryID === inventoryID);
        const gridItem = document.querySelector(`.grid-item .inventoryID[value="${inventoryID}"]`).closest('.grid-item');
        const maxStock = parseInt(gridItem.querySelector('.stock').textContent.split(': ')[1]);

        if (cartItem && cartItem.quantity >= maxStock) {
            gridItem.querySelector('.add-to-cart').disabled = true;
            gridItem.querySelector('.add-to-cart').textContent = 'Max Reached';
        } else {
            gridItem.querySelector('.add-to-cart').disabled = false;
            gridItem.querySelector('.add-to-cart').textContent = 'Add to Cart';
        }

        if (button) {
            button.textContent = 'Added';
            setTimeout(() => {
                updateButtonState(null, inventoryID);
            }, 1000);
        }
    }

    // Initial update of all buttons
    addToCartButtons.forEach(button => {
        const inventoryID = button.closest('.grid-item').querySelector('.inventoryID').value;
        updateButtonState(null, inventoryID);
    });
});

