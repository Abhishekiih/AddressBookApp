package com.bridgelabz.addressBookApp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    //    private String email;
    private String phone;
    private String city;

    private String address;  // ✅ Added
    private String state;    // ✅ Added
    private String zipCode;  // ✅ Added
}