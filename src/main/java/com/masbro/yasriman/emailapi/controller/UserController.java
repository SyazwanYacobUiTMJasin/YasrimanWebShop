// package com.masbro.yasriman.emailapi.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.masbro.yasriman.emailapi.entity.User;
// import com.masbro.yasriman.emailapi.service.UserService;

// import jakarta.mail.MessagingException;

// @RestController
// @RequestMapping("/api/email/accounts")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     @PostMapping("/signup")
//     public EmailResponse signUp(@RequestBody User user) throws MessagingException {
//         String emailContent =  userService.registerUser(user);
//         return new EmailResponse(user.getEmail(), emailContent);
//     }

//     // Inner class for JSON response structure
//     private static class EmailResponse {
//         private String email;
//         private String emailContent;

//         public EmailResponse(String email, String emailContent) {
//             this.email = email;
//             this.emailContent = emailContent;
//         }

//         // Getters and setters (optional, depending on your needs)
//         public String getEmail() {
//             return email;
//         }

//         public void setEmail(String email) {
//             this.email = email;
//         }

//         public String getEmailContent() {
//             return emailContent;
//         }

//         public void setEmailContent(String emailContent) {
//             this.emailContent = emailContent;
//         }
//     }
// }
