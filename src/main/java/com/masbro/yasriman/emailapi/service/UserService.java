package com.masbro.yasriman.emailapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import com.masbro.yasriman.emailapi.entity.User;
import com.masbro.yasriman.emailapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailService;

    public void registerUser(User user) throws jakarta.mail.MessagingException {
        try {
            emailService.sendHtmlEmail(user.getEmail(), "Welcome to Our Plant Shop", generateWelcomeEmail(user));
        } catch (MailException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    private String generateWelcomeEmail(User user) {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Email Notification Sign up</title>"
                + "<style>"
                + "@import url('https://fonts.googleapis.com/css2?family=Inria+Sans:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap');"
                + "@import url('https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap');"
                + "* {"
                + "  margin: 0;"
                + "  padding: 0;"
                + "  box-sizing: border-box;"
                + "  font-family: 'Inria Sans', sans-serif;"
                + "  transition: all 0.3s;"
                + "  overflow-x: hidden;"
                + "  overflow-y: hidden;"
                + "}"
                + "body {"
                + "  background-color: #f5efe6;"
                + "  max-width: 100vw;"
                + "  max-height: 100vh;"
                + "}"
                + ".hero {"
                + "  height: 50vh;"
                + "  color: #1a4d2e;"
                + "  padding: 20px;"
                + "  overflow-y: hidden;"
                + "}"
                + ".buttongroup a {"
                + "  text-decoration: none;"
                + "  color: #f5efe6;"
                + "  font-weight: 400;"
                + "  padding: 10px 10px 10px 25px;"
                + "  gap: 2.7vw;"
                + "  display: flex;"
                + "  align-items: center;"
                + "  justify-content: space-between;"
                + "}"
                + ".buttongroup {"
                + "  display: flex;"
                + "  align-items: center;"
                + "  justify-content: space-between;"
                + "  flex-direction: row;"
                + "  width: 200px;"
                + "  background-color: #1a4d2e;"
                + "  border-radius: 20px;"
                + "  gap: 40px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='hero'>"
                + "  <h1>YASRIMAN</h1>"
                + "  <br>"
                + "  <br>"
                + "  <div class='subtitle'>"
                + "    <h4>Thanks for signing up with us!</h4>"
                + "    Come visit our products ranging from indoor plants to tools."
                + "  </div>"
                + "  <br><br>"
                + "  <div class='buttongroup'>"
                + "    <a href='https://plantnursery-2bc3abe6f7b5.herokuapp.com/inventory?action=viewallproducts' class='shopnowbutton'>"
                + "      SHOP NOW"
                + "    </a>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
