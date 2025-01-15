console.log("test12");

document.addEventListener("DOMContentLoaded", function () {
    // Form submission handler
    document.getElementById("profile-form").addEventListener("submit", function (event) {
      if (!validateForm()) {
        event.preventDefault(); // Stop form submission if validation fails
      }
    });
  });
  
  document.getElementById('submit-btn').addEventListener('click', function(event) {
    if (!validateForm()) {
        event.preventDefault();
    } 
});

  document.getElementById('cancel-btn').addEventListener('click', function(event) {
    if (confirm("Are you sure you want to cancel?")) {
        window.location.href = "/";
    } 
});


  function validateForm() {
    // event.preventDefault();
        const firstName = document.getElementById("firstname").value.trim();
        const lastName = document.getElementById("lastname").value.trim();
        const username = document.getElementById("username").value.trim(); 
        const phone = document.getElementById("phone").value.trim();
        const street = document.querySelector('input[name="street"]').value.trim();
        const state = document.querySelector('input[name="state"]').value.trim();
        const city = document.querySelector('input[name="city"]').value.trim();
        const postalCode = document.querySelector('input[name="postalcode"]').value.trim();
 
    
         // Check for empty fields first
      if (firstName === "") {
        alert("First name cannot be empty.");
        return false;
    }

    if (lastName === "") {
        alert("Last name cannot be empty.");
        return false;
    }

    if (username === "") {
        alert("Username cannot be empty.");
        return false;
    } 

    if (phone === "") {
        alert("Phone number cannot be empty.");
        return false;
    }

     // Check for empty fields first
     if (street === "") {
        alert("Street cannot be empty.");
        return false;
    }

    if (state === "") {
        alert("State cannot be empty.");
        return false;
    }

    if (city === "") {
        alert("City cannot be empty.");
        return false;
    }

    if (postalCode === "") {
        alert("Postal Code cannot be empty.");
        return false;
    } 


        // Regular expressions for validation
        const nameRegex = /^[a-zA-Z]{3,10}$/;
        const usernameRegex = /^[a-zA-Z0-9_]{3,30}$/; 
        const malaysiaPhoneRegex = /^\d{9,10}$/;
        const addressRegex = /^[a-zA-Z0-9\s,]{3,50}$/;
        const postalCodeRegex = /^\d{5}$/;
    
      if (!nameRegex.test(firstName)) {
        alert("First name must be between 3 and 10 characters long and contain only letters.");
        return false;
      }
    
      if (!nameRegex.test(lastName)) {
        alert("Last name must be between 3 and 10 characters long and contain only letters.");
        return false;
      }
    
      if (!usernameRegex.test(username)) {
        alert("Username must be between 3 and 30 characters long and can only contain letters, numbers, and underscore.");
        return false;
      }
      
    
      if (!malaysiaPhoneRegex.test(phone)) {
        alert("Please enter a valid Malaysia phone number (e.g., +60123456789).");
        return false;
      }
    
      if (!addressRegex.test(street)) {
            alert("Street must be 3-50 characters long and can contain letters, numbers, and spaces.");
            return false;
        }

        if (!addressRegex.test(state)) {
            alert("State must be 3-50 characters long and can contain letters, numbers, and spaces.");
            return false;
        }

        if (!addressRegex.test(city)) {
            alert("City must be 3-50 characters long and can contain letters, numbers, and spaces.");
            return false;
        }

        if (!postalCodeRegex.test(postalCode)) {
            alert("Postal code must be 5 digits long and contain only numbers.");
            return false;
        }
        
      return true;
    }
    
   


