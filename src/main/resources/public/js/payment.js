document.getElementById('paymentForm').addEventListener('submit', function(event) {
    var fname = document.getElementById('fname');
    var lname = document.getElementById('lname');
    var address = document.getElementById('address');
    var postcode = document.getElementById('postcode');
    var city = document.getElementById('city');
    var state = document.getElementById('state');
    
    var isValid = true;
    
    if (fname.value.trim() === '') {
        fname.style.borderColor = 'red';
        isValid = false;
    } else {
        fname.style.borderColor = '';
    }
    
    if (lname.value.trim() === '') {
        lname.style.borderColor = 'red';
        isValid = false;
    } else {
        lname.style.borderColor = '';
    }
    
    if (address.value.trim() === '') {
        address.style.borderColor = 'red';
        isValid = false;
    } else {
        address.style.borderColor = '';
    }
    
    if (postcode.value.trim() === '') {
        postcode.style.borderColor = 'red';
        isValid = false;
    } else {
        postcode.style.borderColor = '';
    }
    
    if (city.value.trim() === '') {
        city.style.borderColor = 'red';
        isValid = false;
    } else {
        city.style.borderColor = '';
    }
    
    if (state.value.trim() === '') {
        state.style.borderColor = 'red';
        isValid = false;
    } else {
        state.style.borderColor = '';
    }
    
    if (!isValid) {
        event.preventDefault(); // Prevent form submission if any field is empty
    }
  });
  
  $(function() {
      $('[data-toggle="tooltip"]').tooltip()
    })

    