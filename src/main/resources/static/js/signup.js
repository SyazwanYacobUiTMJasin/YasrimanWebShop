
document.getElementById('submit-btn').addEventListener('click', 
  function(event) {
  
      event.preventDefault();
      validateForm();
});

function validateForm() {
    const firstName = document.getElementById("firstname").value.trim();
    const lastName = document.getElementById("lastname").value.trim();
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmpassword").value;
    const phone = document.getElementById("phone").value.trim();
    
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

    if (email === "") {
        alert("Email cannot be empty.");
        return false;
    }

    if (password === "") {
        alert("Password cannot be empty.");
        return false;
    }

    if (confirmPassword === "") {
        alert("Confirm password cannot be empty.");
        return false;
    }

    if (phone === "") {
        alert("Phone number cannot be empty.");
        return false;
    }

    // Regular expressions for validation
    const nameRegex = /^[a-zA-Z]{3,10}$/;
    const usernameRegex = /^[a-zA-Z0-9_]{3,30}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,255}$/;
    const malaysiaPhoneRegex = /^\+60\d{9,10}$/;
  
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
  
    if (!emailRegex.test(email)) {
      alert("Please enter a valid email address.");
      return false;
    }
  
    if (!passwordRegex.test(password)) {
      alert("Password must be between 8 and 15 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
      return false;
    }
  
    if (password !== confirmPassword) {
      alert("Passwords do not match.");
      return false;
    }
  
    if (!malaysiaPhoneRegex.test(phone)) {
      alert("Please enter a valid Malaysia phone number (e.g., +60123456789).");
      return false;
    }
  
    return true;
  }
  
 
  
  document.addEventListener("DOMContentLoaded", function() {
    const eyeToggles = document.querySelectorAll(".eyeToggle");

    eyeToggles.forEach(function(eyeToggle) {
        eyeToggle.addEventListener("click", function() {
            const passwordField = this.previousElementSibling;
            
            if (passwordField.type === "password") {
                passwordField.type = "text";
                this.src = "./images/eyeslash.svg"; // Change to the 'show password' icon
                this.alt = "Hide Password";
            } else {
                passwordField.type = "password";
                this.src = "./images/eyenormal.svg"; // Change to the 'hide password' icon
                this.alt = "Show Password";
            }
        });
    });
});

function togglePasswordVisibility(inputId, eyeIcon) {
    var passwordInput = document.getElementById(inputId);

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.src = "./images/eyeslash.svg"; // Change to the 'show password' icon
        eyeIcon.alt = "Hide Password";
    } else {
        passwordInput.type = "password";
        eyeIcon.src = "./images/eyenormal.svg"; // Change to the 'hide password' icon
        eyeIcon.alt = "Show Password";
    }
}