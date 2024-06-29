package com.masbro.yasriman.emailapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    public JavaMailSender mailSender;

    public void sendHtmlEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom("yasrimanonlineshop@gmail.com");

        // Add inline image
        helper.addInline("arrowIcon", new ClassPathResource("static/images/arrowIcon.png"));

        mailSender.send(mimeMessage);
    }
}
