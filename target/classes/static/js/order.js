function updateStatus(checkbox, statusId) {
    var status = document.getElementById(statusId);
    if (checkbox.checked) {
      status.innerHTML = 'Shipped';
    } else {
      status.innerHTML = 'Process';
    }
  }

  let editModal = document.getElementById('editModal');

function showDetails() {
  editModal.style.display = 'block';
}

function closeEditModal() {
  editModal.style.display = 'none';
}


  