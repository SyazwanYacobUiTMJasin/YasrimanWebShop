document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const gridItems = document.querySelectorAll('.grid-item');
    const noResults = document.getElementById('noResults');

    searchInput.addEventListener('keyup', function () {
        const filter = searchInput.value.toLowerCase();
        let hasVisibleItems = false;

        gridItems.forEach(function (item) {
            const title = item.querySelector('h3').textContent.toLowerCase();

            if (title.indexOf(filter) > -1) {
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
    const cartButtons = document.querySelectorAll('.add-to-cart');

    cartButtons.forEach(button => {
        button.addEventListener('click', function () {
            const gridItem = button.parentElement;
            const title = gridItem.querySelector('h3').textContent;
            const price = parseFloat(gridItem.querySelector('.price').textContent.replace('RM', ''));
            const imgSrc = gridItem.querySelector('img').src;

            addToCart({ title, price, imgSrc });

            button.textContent = 'Added';
            setTimeout(() => {
                button.textContent = 'Add to Cart';
            }, 1000);
        });
    });

    function addToCart(item) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const existingItem = cart.find(cartItem => cartItem.title === item.title);

        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            item.quantity = 1;
            cart.push(item);
        }

        localStorage.setItem('cart', JSON.stringify(cart));
    }
});

