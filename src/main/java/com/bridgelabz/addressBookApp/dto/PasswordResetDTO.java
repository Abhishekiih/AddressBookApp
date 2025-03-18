package com.bridgelabz.addressBookApp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDTO {

    @NotBlank(message = "Current phone is required")
    private String currentPhone;  // 🔹 Used only for reset password (Not needed for forgot password)

    @NotBlank(message = "New phone is required")
    private String newPhone;
}