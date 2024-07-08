
function sortTable() {
    const table = document.getElementById("tableid");
    const rows = Array.from(table.rows).slice(1);
    const sortOrderElement = document.getElementById("sort-order");
    const sortOrder = sortOrderElement.value === "asc" ? "desc" : "asc";
    sortOrderElement.value = sortOrder;

    rows.sort((a, b) => {
        const paymentStatusA = a.cells[4].innerText.toUpperCase();
        const paymentStatusB = b.cells[4].innerText.toUpperCase();
        
        if (paymentStatusA < paymentStatusB) {
            return sortOrder === "asc" ? -1 : 1;
        }
        if (paymentStatusA > paymentStatusB) {
            return sortOrder === "asc" ? 1 : -1;
        }
        return 0;
    });

    rows.forEach(row => table.appendChild(row));
}

function showDeleteNotification() {
    alert("You need to change the payment status to 'Not Approved' before deleting the order.");
}

document.addEventListener('DOMContentLoaded', function() {
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            if (this.hasAttribute('disabled')) {
                event.preventDefault();
                showDeleteNotification();
            }
        });
    });
});