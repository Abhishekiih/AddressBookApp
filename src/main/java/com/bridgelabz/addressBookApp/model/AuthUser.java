package com.bridgelabz.addressBookApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuthUser {

    String name;
    String email;
    String phone;
    String hashPass;
    String token;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public AuthUser() {
    }

    public AuthUser(String name, String email, String phone, String hashPass) {
        this.name=name;
        this.email = email;
        this.phone = phone;
        this.hashPass = hashPass;

        this.token="";
        this.id = null;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone= phone;
    }

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
    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getHashPass() {
        return hashPass;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
