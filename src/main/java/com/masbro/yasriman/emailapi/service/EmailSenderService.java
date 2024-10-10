// package com.masbro.yasriman.emailapi.service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.mail.MailException;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.stereotype.Service;

// import jakarta.annotation.PostConstruct;
// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;

// @Service
// public class EmailSenderService {

//     @Autowired
//     public JavaMailSender mailSender;

//     private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

//     @Value("${mailgun.domain}")
//     private String mailgunDomain;

//     @Value("${spring.mail.host}")
//     private String mailHost;

//     @Value("${spring.mail.port}")
//     private int mailPort;

//     @Value("${spring.mail.username}")
//     private String mailUsername;

//     @Value("${spring.mail.password}")
//     private String mailPassword;

//     public void sendHtmlEmail(String toEmail, String subject, String body) throws MessagingException {
//         try {
//             MimeMessage mimeMessage = mailSender.createMimeMessage();
//             MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

//             helper.setTo(toEmail);
//             helper.setSubject(subject);
//             helper.setText(body, true);
//             helper.setFrom("noreply@" + mailgunDomain);

//             // Add inline image
//             // helper.addInline("arrowIcon", new ClassPathResource("static/images/arrowIcon.png"));

//             mailSender.send(mimeMessage);
//             logger.info("Email sent successfully to: {}", toEmail);
//         }
//         catch (MessagingException | MailException e) {
//             logger.error("Failed to send email to: {}. Error: {}", toEmail, e.getMessage());
//             // You might want to throw a custom exception here or handle it as appropriate for your application
//         }
//     }

//     @PostConstruct
//     public void logConfig() {
//         System.out.println("Mail Host: " + mailHost);
//         System.out.println("Mail Port: " + mailPort);
//         System.out.println("Mail Username: " + mailUsername);
//         System.out.println("Mailgun Domain: " + mailgunDomain);
//     }
// }
