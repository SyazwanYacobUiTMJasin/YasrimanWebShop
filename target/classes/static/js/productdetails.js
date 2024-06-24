document.addEventListener("DOMContentLoaded", function() {
            const quantityInput = document.getElementById('quantity');
            const minusButton = document.querySelector('.quantity-minus');
            const plusButton = document.querySelector('.quantity-plus');

            minusButton.addEventListener('click', function() {
                if (quantityInput.value > 1) {
                    quantityInput.value = parseInt(quantityInput.value) - 1;
                }
            });

            plusButton.addEventListener('click', function() {
                quantityInput.value = parseInt(quantityInput.value) + 1;
            });
        });

