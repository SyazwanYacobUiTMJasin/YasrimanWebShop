package com.masbro.yasriman.emailapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masbro.yasriman.emailapi.entity.User;
import com.masbro.yasriman.emailapi.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email/accounts")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) throws MessagingException {
        String emailContent =  userService.registerUser(user);
        return emailContent;
    }
}
