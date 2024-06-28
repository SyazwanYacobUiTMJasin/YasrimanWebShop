
window.onload = function() {
        var audio = document.getElementById("notificationSound");
        audio.play().then(() => {
            // Unmute the audio after it starts playing
            audio.muted = false;
        }).catch(error => {
            console.error("Audio playback failed:", error);
        });
    };
   
localStorage.clear();
