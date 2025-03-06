package com.bridgelabz.addressBookApp.repository;

import com.bridgelabz.addressBookApp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}