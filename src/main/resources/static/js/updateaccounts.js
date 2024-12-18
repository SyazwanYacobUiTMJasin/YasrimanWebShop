console.log("test12");

function validateUpdateForm() {
    // Get form input values
    const username = document.querySelector('input[name="username"]').value.trim();
    const firstName = document.querySelector('input[name="firstname"]').value.trim();
    const lastName = document.querySelector('input[name="lastname"]').value.trim();
    const phone = document.querySelector('input[name="phone"]').value.trim();
    const street = document.querySelector('input[name="street"]').value.trim();
    const state = document.querySelector('input[name="state"]').value.trim();
    const city = document.querySelector('input[name="city"]').value.trim();
    const postalCode = document.querySelector('input[name="postalcode"]').value.trim();

    // Regular expressions for validation
    const nameRegex = /^[a-zA-Z]{3,10}$/;
    const usernameRegex = /^[a-zA-Z0-9_]{3,30}$/;
    const malaysiaPhoneRegex = /^\d{9,10}$/;
    const addressRegex = /^[a-zA-Z0-9\s]{3,50}$/;
    const postalCodeRegex = /^\d{5}$/;

    // Validation checks
    if (!usernameRegex.test(username)) {
        alert("Username must be between 3 and 30 characters long and can only contain letters, numbers, and underscore.");
        return false;
    }

    if (!nameRegex.test(firstName)) {
        alert("First name must be between 3 and 10 characters long and contain only letters.");
        return false;
    }

    if (!nameRegex.test(lastName)) {
        alert("Last name must be between 3 and 10 characters long and contain only letters.");
        return false;
    }

    if (!malaysiaPhoneRegex.test(phone)) {
        alert("Please enter a valid Malaysia phone number (e.g., +60-123456789).");
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
        alert("Postal code must be 5 digits long.");
        return false;
    }

    return true;
}



