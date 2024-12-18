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

document.getElementById('submit-btn').addEventListener('click', 
    function(event) {
        
        if(!validateForm())
        {
          event.preventDefault();
        } 
  });

function validateForm() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    
    if (email === "") {
        alert("Email cannot be empty.");
        return false;
    }

    if (password === "") {
        alert("Password cannot be empty.");
        return false;
    }
    
    // Regular expression for email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,255}$/;
    
  
    if (!emailRegex.test(email)) {
      alert("Please enter a valid email address.");
      return false;
    }
  
    if (!passwordRegex.test(password)) {
        alert("Password must be between 8 and 15 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
        return false;
      }
      
    if (password.length < 8) {
      alert("Password must be at least 8 characters long.");
      return false;
    }
  
    return true;
  }
  
   


  