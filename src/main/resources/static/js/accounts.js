// accounts.js

function confirmDelete(accountId) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        if (confirm("Are you sure you want to delete this account?")) {
            window.location.href = "deletecustomeraccount?uid=" + accountId;
        }
    } else {
        alert("Only supervisors can delete accounts.");
    }
}

function confirmUpdate(accountId) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        console.log("Supervisor id: " + accountId + "updating accounts");
    } else {
        alert("Only supervisors can update accounts.");
    }
}

function confirmAction(message, formId) {
    if (confirm(message)) {
        document.getElementById(formId).submit();
    }
}

document.addEventListener('DOMContentLoaded', function() {
    var approveButtons = document.querySelectorAll('.approve-btn');
    var rejectButtons = document.querySelectorAll('.reject-btn');
    
    approveButtons.forEach(function(button) {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            var accountId = this.getAttribute('data-id');
            var accountUsername = this.getAttribute('data-username');
            var accountEmail = this.getAttribute('data-email');
            confirmApprove(accountId, accountUsername, accountEmail);
        });
    });
    
    rejectButtons.forEach(function(button) {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            var accountId = this.getAttribute('data-id');
            var accountUsername = this.getAttribute('data-username');
            var accountEmail = this.getAttribute('data-email');
            confirmDeletePending(accountId, accountUsername, accountEmail);
        });
    });
});

function confirmApprove(accountId, accountUsername, accountEmail) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        if (confirm("Are you sure you want to approve this account?")) {
            window.location.href = "approve/" + accountId + "/" + encodeURIComponent(accountUsername) + "/" + encodeURIComponent(accountEmail);
        }
    } else {
        alert("Only supervisors can approve accounts.");
    }
}

function confirmDeletePending(accountId, accountUsername, accountEmail) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        if (confirm("Are you sure you want to reject and delete this account?")) {
            // window.location.href = "deletecustomeraccount?uid=" + accountId;
            window.location.href = "reject/" + accountId + "/" + encodeURIComponent(accountUsername) + "/" + encodeURIComponent(accountEmail);
        }
    } else {
        alert("Only supervisors can reject accounts.");
    }
}