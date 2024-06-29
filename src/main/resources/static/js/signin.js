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

function validateForm() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    
    // Regular expression for email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
    if (!emailRegex.test(email)) {
      alert("Please enter a valid email address.");
      return false;
    }
  
    if (password.length < 8) {
      alert("Password must be at least 8 characters long.");
      return false;
    }
  
    return true;
  }
  
   


  