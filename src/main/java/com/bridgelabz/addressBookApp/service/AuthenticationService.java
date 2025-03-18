package com.bridgelabz.addressBookApp.service;

import com.bridgelabz.addressBookApp.dto.AuthUserDTO;
import com.bridgelabz.addressBookApp.dto.LoginDTO;
import com.bridgelabz.addressBookApp.model.AuthUser;
import com.bridgelabz.addressBookApp.repository.AuthUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthUserRepository userRepository;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public AuthenticationService(AuthUserRepository userRepository, EmailService emailService, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtTokenService = jwtTokenService;
    }

    // 🔹 Register User
    public String register(AuthUserDTO user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already registered";
        }

        String hashPass = bcrypt.encode(user.getPhone()); // 🔹 Hashing phone number as password
        AuthUser newUser = new AuthUser(user.getName(), user.getEmail(), user.getPhone(), hashPass);
        userRepository.save(newUser);

        emailService.sendEmail(user.getEmail(), "Registration Successful", "Welcome, " + user.getName() + "!");
        return "User registered successfully";
    }

    // 🔹 Login User (Using Email & Phone)
    public String login(LoginDTO user) {
        Optional<AuthUser> foundUserOpt = userRepository.findByEmail(user.getEmail());

        if (foundUserOpt.isEmpty()) {
            return "User not registered";
        }

        AuthUser foundUser = foundUserOpt.get();

        if (!bcrypt.matches(user.getPhone(), foundUser.getHashPass())) {
            return "Invalid phone number";
        }

        String token = jwtTokenService.createToken(foundUser.getId());
        foundUser.setToken(token);
        userRepository.save(foundUser);

        return "User logged in\nToken: " + token;
    }

    // 🔹 Forgot Password (Change Password Without Logging In)
    public String forgotPassword(String email, String newPhone) {
        Optional<AuthUser> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        AuthUser user = userOpt.get();
        user.setHashPass(bcrypt.encode(newPhone));
        userRepository.save(user);

        emailService.sendEmail(email, "Password Reset", "Your password has been changed successfully!");
        return "Password changed successfully!";
    }

    // 🔹 Reset Password (Change Password While Logged In)
    public String resetPassword(String email, String currentPhone, String newPhone) {
        Optional<AuthUser> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        AuthUser user = userOpt.get();

        if (!bcrypt.matches(currentPhone, user.getHashPass())) {
            return "Current phone number is incorrect!";
        }

        user.setHashPass(bcrypt.encode(newPhone));
        userRepository.save(user);

        return "Password reset successfully!";
    }
}