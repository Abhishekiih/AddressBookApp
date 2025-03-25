package com.bridgelabz.addressBookApp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ContactDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

//    @Email(message = "Invalid email format")
//    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "City is required")
    private String city;

    private String address;
    private String state;   // Added
    private String zipCode; // Added
}