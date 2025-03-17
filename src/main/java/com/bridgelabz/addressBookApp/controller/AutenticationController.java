package com.bridgelabz.addressBookApp.controller;

import com.bridgelabz.addressBookApp.dto.AuthUserDTO;
import com.bridgelabz.addressBookApp.service.AuthenticationService;
import com.bridgelabz.addressBookApp.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.addressBookApp.dto.LoginDTO;

@RestController
public class AutenticationController {
    EmailService emailService;
    AuthenticationService authenticationService;

    public AutenticationController(EmailService emailService, AuthenticationService authenticationService) {
        this.emailService = emailService;
        this.authenticationService = authenticationService;
    }

    //============================UC9(Register and Login for a User)
    @PostMapping(path = "/register")
    public String register(@RequestBody AuthUserDTO user){
        return authenticationService.register(user);
    }

    @PostMapping(path ="/login")
    public String login(@RequestBody LoginDTO user){
        return authenticationService.login(user);
    }




}
