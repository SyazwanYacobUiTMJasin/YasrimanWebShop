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
