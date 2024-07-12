
window.onload = function() {
        var audio = document.getElementById("notificationSound");
        audio.play().then(() => {
            // Unmute the audio after it starts playing
            audio.muted = false;
        }).catch(error => {
            console.error("Audio playback failed:", error);
        });
    };

    document.addEventListener('DOMContentLoaded', function () {
        // Retrieve the subtotal from local storage
        let subtotal = parseFloat(localStorage.getItem('subtotal')) || 0;
    
        // Display the subtotal in the respective element
        document.getElementById('amountPaid').textContent = subtotal.toFixed(2);
    });

    
localStorage.clear();
