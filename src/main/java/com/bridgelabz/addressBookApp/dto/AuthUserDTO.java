package com.bridgelabz.addressBookApp.dto;


public class AuthUserDTO {
    private String name;
    private String email;
    private String phone;
    private Long id;

    // Default constructor
    public AuthUserDTO() {
    }

    // Constructor with parameters (excluding ID)
    public AuthUserDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = null; // ID is set to null by default
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Long getId() {
        return id;
    }
}
