package com.bridgelabz.addressBookApp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String phone;  // 🔹 Phone will be used as password
    private String hashPass;
    private String token;

    public AuthUser() {}

    public AuthUser(String name, String email, String phone, String hashPass) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hashPass = hashPass;
        this.token = "";
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getHashPass() { return hashPass; }
    public String getToken() { return token; }

    public void setHashPass(String hashPass) { this.hashPass = hashPass; }
    public void setToken(String token) { this.token = token; }
}