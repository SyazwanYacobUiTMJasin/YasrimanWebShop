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

function confirmApprove(accountId) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        if (confirm("Are you sure you want to approve this account?")) {
            window.location.href = "approve/" + accountId;
        }
    } else {
        alert("Only supervisors can approve accounts.");
    }
}

function confirmDeletePending(accountId) {
    var role = document.getElementById("userRole").value;
    if (role === 'Supervisor') {
        if (confirm("Are you sure you want to reject and delete this account?")) {
            window.location.href = "deletecustomeraccount?uid=" + accountId;
        }
    } else {
        alert("Only supervisors can reject accounts.");
    }
}