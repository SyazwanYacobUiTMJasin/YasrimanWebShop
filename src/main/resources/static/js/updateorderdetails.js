document.addEventListener("DOMContentLoaded", function() {
    var orderStatusSelect = document.getElementById("orderStatus");
    var paymentStatusSelect = document.getElementById("paymentStatus");
    
    function updateSelects(changedSelect) {
        var paymentStatus = paymentStatusSelect.value;
        var orderStatus = orderStatusSelect.value;

        if (changedSelect === 'order') {
            if (orderStatus === "CANCELLED") {
                paymentStatusSelect.value = "NOTAPPROVED";
                paymentStatusSelect.disabled = true;
            } else {
                paymentStatusSelect.disabled = false;
                if (paymentStatus === "NOTAPPROVED") {
                    paymentStatusSelect.value = "PENDING";
                }
            }
        } else if (changedSelect === 'payment') {
            if (paymentStatus === "NOTAPPROVED") {
                orderStatusSelect.value = "CANCELLED";
                orderStatusSelect.disabled = true;
            } else {
                orderStatusSelect.disabled = false;
                if (orderStatus === "CANCELLED") {
                    orderStatusSelect.value = "PROCESS";
                }
            }
        }
    }

    function onOrderStatusChange() {
        updateSelects('order');
    }

    function onPaymentStatusChange() {
        updateSelects('payment');
    }

    // Initial call to set correct state
    updateSelects('order');
    updateSelects('payment');

    // Add event listeners
    orderStatusSelect.addEventListener("change", onOrderStatusChange);
    paymentStatusSelect.addEventListener("change", onPaymentStatusChange);
});

// CHECK UPDATE ORDER ALREADY CLICKED

// Function to get the order update status from localStorage
function getOrderUpdateStatus(orderId) {
    return localStorage.getItem(`orderUpdate_${orderId}`) === 'true';
}

// Function to set the order update status in localStorage
function setOrderUpdateStatus(orderId) {
    localStorage.setItem(`orderUpdate_${orderId}`, 'true');
}

// Function to check if this is a packaging+approved case that needs one-time update restriction
function isRestrictedUpdate(orderStatus, paymentStatus) {
    return orderStatus === 'PACKAGING' && paymentStatus === 'APPROVED';
}

// Function to check if update is allowed
function isUpdateAllowed(orderStatus, paymentStatus, hasBeenUpdated) {
    // If it's not a packaging+approved case, always allow update
    if (!isRestrictedUpdate(orderStatus, paymentStatus)) {
        return true;
    }
    
    // For packaging+approved, only allow if it hasn't been updated before
    return !hasBeenUpdated;
}

// Function to get orderId from the hidden input field
function getOrderId() {
    const hiddenInput = document.querySelector('input[name="orderId"]');
    return hiddenInput ? hiddenInput.value : null;
}

// Function to update button state
function updateButtonState() {
    const form = document.getElementById('updateOrderForm');
    const updateButton = form.querySelector('.update-btn');
    const orderId = getOrderId();
    
    if (!orderId) {
        console.error('Order ID not found in the form');
        return;
    }

    const orderStatus = document.getElementById('orderStatus').value;
    const paymentStatus = document.getElementById('paymentStatus').value;
    const hasBeenUpdated = getOrderUpdateStatus(orderId);

    const canUpdate = isUpdateAllowed(orderStatus, paymentStatus, hasBeenUpdated);
    
    updateButton.disabled = !canUpdate;
    updateButton.style.opacity = canUpdate ? '1' : '0.5';
    updateButton.style.cursor = canUpdate ? 'pointer' : 'not-allowed';
}

// Handle form submission
document.getElementById('updateOrderForm').addEventListener('submit', function(e) {
    const orderId = getOrderId();
    
    if (!orderId) {
        console.error('Order ID not found in the form');
        return;
    }

    const orderStatus = document.getElementById('orderStatus').value;
    const paymentStatus = document.getElementById('paymentStatus').value;
    
    // Only store the update status if it's a packaging+approved case
    if (isRestrictedUpdate(orderStatus, paymentStatus)) {
        setOrderUpdateStatus(orderId);
    }
});

// Add event listeners for status changes
document.getElementById('orderStatus').addEventListener('change', updateButtonState);
document.getElementById('paymentStatus').addEventListener('change', updateButtonState);

// Initialize button state on page load
document.addEventListener('DOMContentLoaded', updateButtonState);