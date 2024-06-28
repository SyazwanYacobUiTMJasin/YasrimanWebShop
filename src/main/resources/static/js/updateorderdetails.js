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