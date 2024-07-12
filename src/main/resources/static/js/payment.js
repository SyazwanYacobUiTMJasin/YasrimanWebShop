
document.addEventListener('DOMContentLoaded', function () {
              // Retrieve cart data from local storage
              let cart = JSON.parse(localStorage.getItem('cart')) || [];

              // Initialize variables for subtotal and total
              let subtotal = 0;

              // Calculate the subtotal
              cart.forEach(item => {
                  subtotal += item.price * item.quantity;
              });

              // Calculate the total
              let total = subtotal;

              // Display the values in the respective elements
              document.getElementById('subtotal').textContent = subtotal.toFixed(2);
              document.getElementById('total').textContent = total.toFixed(2);

              localStorage.setItem('subtotal', subtotal.toFixed(2));
          });
          
          
var popupContainer = document.getElementById("popupContainer");

      function showDetails() {
        popupContainer.style.display = "block";
      }

      function closeEditModal() {
        popupContainer.style.display = "none";
      }
      function confirmCancel() {
        var userConfirmation = confirm("Are you sure you want to cancel the payment and go back to the cart?");
        if (userConfirmation) {
          window.location.href = 'cart';
        }
      }
      
document.addEventListener('DOMContentLoaded', function () {
        const payNowBtn = document.getElementById('payNowBtn');
        const paymentForm = document.getElementById('paymentForm');
		const errorMessage = document.getElementById('error-message');
		
        payNowBtn.addEventListener('click', function () {
          let allValid = true;
          let allTextValid = true;
          const textinputs = paymentForm.querySelectorAll('input[type="text"]');
          const fileinputs = paymentForm.querySelectorAll('input[type="file"]');
          
          textinputs.forEach(textinputs => {
            if (!textinputs.value) {
              textinputs.style.border = '2px solid red';
              allValid = false;
              allTextValid = false;
            } else {
              
              textinputs.style.border = ''; 
              
            }
          });
          
			fileinputs.forEach(fileinputs => {
            if (!fileinputs.value) {
              fileinputs.style.border = '2px solid red';
              allValid = false;
            } else {
              fileinputs.style.border = '';
            }
          });
          
          if (!allTextValid) {
             errorMessage.style.display = 'block'; 
          }else if (allTextValid) {
			errorMessage.style.display = 'none';
            showDetails();
          }
          
          if (allValid) { 
            paymentForm.submit();
          }
        });
      });

      
    