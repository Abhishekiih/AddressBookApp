package com.bridgelabz.addressBookApp.repository;

import com.bridgelabz.addressBookApp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
