function validateForm() {
    const firstName = document.getElementById("firstname").value.trim();
    const lastName = document.getElementById("lastname").value.trim();
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmpassword").value;
    const phone = document.getElementById("phone").value.trim();
    
    // Regular expressions for validation
    const nameRegex = /^[a-zA-Z]{3,10}$/;
    const usernameRegex = /^[a-zA-Z0-9_]{3,}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
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
      alert("Username must be at least 3 characters long and can only contain letters, numbers, and underscore.");
      return false;
    }
  
    if (!emailRegex.test(email)) {
      alert("Please enter a valid email address.");
      return false;
    }
  
    if (!passwordRegex.test(password)) {
      alert("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
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
            const passwordField = this.parentElement.querySelector("input[type='password']");
            
            if (this.src.includes("eyeslash.svg")) {
                this.src = "./images/eyenormal.svg";
                
            } else if (this.src.includes("eyenormal.svg")) {
                this.src = "./images/eyeslash.svg";
            }
        });
    });
});

function togglePasswordVisibility(inputId) {
    var passwordInput = document.getElementById(inputId);
    var eyeIcon = passwordInput.nextElementSibling;

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.src = "./images/eyenormal.svg";
        eyeIcon.alt = "Hide Password";
    } else {
        passwordInput.type = "password";
        eyeIcon.src = "./images/eyeslash.svg";
        eyeIcon.alt = "Show Password";
    }
}
