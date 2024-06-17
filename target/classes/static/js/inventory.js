document.querySelector('.header-container button').addEventListener('click', function() {
    document.getElementById("createNewModal").style.display = "block";
  });
  
  function closeNewModal() {
    document.getElementById("createNewModal").style.display = "none";
  }
  
  function confirmNew() {
    let type = document.getElementById("newTypeSelect").value;
    let imageInput = document.getElementById("newImageInput");
    let name = document.getElementById("newNameInput").value;
    let stock = document.getElementById("newStockInput").value;
  
    if (imageInput.files.length > 0) {
      let file = imageInput.files[0];
      let reader = new FileReader();
      reader.onload = function (e) {
        addRow(type, e.target.result, name, stock);
      };
      reader.readAsDataURL(file);
    } else {
      // Handle case with no image selected
      addRow(type, '', name, stock);
    }
  }
  
  function addRow(type, imageSrc, name, stock) {
    let table = document.querySelector('.table-container table');
    let newRow = table.insertRow(-1);
    newRow.innerHTML = `
      <td>New</td>
      <td>${type}</td>
      <td><img src="${imageSrc}" alt="${name}" style="width:50px; height:auto;"></td>
      <td>${name}</td>
      <td>${stock}</td>
      <td><button class="edit-btn" onclick="openEditModal(this)">Edit</button></td>
      <td><button class="delete-btn" onclick="deleteRow(this)">Delete</button></td>
    `;
    closeNewModal();
  }
  
  window.onclick = function(event) {
    if (event.target == document.getElementById("createNewModal")) {
      closeNewModal();
    }
  }
  
  
  let editModal = document.getElementById("editModal");
      let idInput = document.getElementById("idInput");
      let typeSelect = document.getElementById("typeSelect");
      let imageInput = document.getElementById("imageInput");
      let nameInput = document.getElementById("nameInput");
      let stockInput = document.getElementById("stockInput");
      let currentRow;
  
      function openEditModal(button) {
        currentRow = button.parentNode.parentNode;
        let cells = currentRow.getElementsByTagName("td");
        idInput.value = cells[0].innerText;
        typeSelect.value = cells[1].innerText.toLowerCase();
        nameInput.value = cells[3].innerText;
        stockInput.value = cells[4].innerText;
        editModal.style.display = "block";
      }
  
      function closeEditModal() {
        editModal.style.display = "none";
      }
  
      function confirmEdit() {
        let cells = currentRow.getElementsByTagName("td");
        cells[1].innerText = typeSelect.value;
        cells[2].innerHTML = `<img src="${imageInput.value}" alt="${nameInput.value}">`;
        cells[3].innerText = nameInput.value;
        cells[4].innerText = stockInput.value;
        closeEditModal();
      }
  
      function deleteRow(button) {
        let row = button.parentNode.parentNode;
        row.parentNode.removeChild(row);
      }
  
      function deleteRow(button) {
          if (confirm("Are you sure you want to delete this item?")) {
            let row = button.parentNode.parentNode;
            row.parentNode.removeChild(row);
          }
        }
  
      window.onclick = function(event) {
        if (event.target == editModal) {
          closeEditModal();
        }
      }