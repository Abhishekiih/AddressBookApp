package com.bridgelabz.addressBookApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class LoginDTO {

    @Email(message = "User email not correct")
    @NotBlank(message = "email required")
    String email;

    @NotBlank(message = "phone required")
    @Pattern(regexp = "^(\\+?[1-9]{1,4}[ -]?)?(\\(?\\d{1,4}\\)?[ -]?)?(\\d{1,4}[ -]?)?\\d{1,4}[ -]?\\d{1,4}$", message = "invalid password")
    String phone;

    LoginDTO(String email, String password){
        this.email = email;
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}