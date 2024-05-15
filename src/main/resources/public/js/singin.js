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
