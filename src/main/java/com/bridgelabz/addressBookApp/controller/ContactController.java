package com.bridgelabz.addressBookApp.controller;

import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.service.ContactService;
import lombok.extern.slf4j.Slf4j; // Lombok import for SLF4J logging
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j  // This annotation enables logging
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    // Constructor-based Dependency Injection
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Fetching all contacts");
        List<ContactDTO> contacts = contactService.getAllContacts();
        log.info("Retrieved {} contacts", contacts.size()); // Log the size of the result
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        Optional<ContactDTO> contactDTO = contactService.getContactById(id);
        return contactDTO.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Contact with ID: {} not found", id); // Warn log if not found
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO);
        ContactDTO savedContact = contactService.addContact(contactDTO);
        log.info("Added new contact with ID: {}", savedContact.getId());
        return ResponseEntity.ok(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        log.info("Updating contact with ID: {}", id);
        Optional<ContactDTO> updatedContact = contactService.updateContact(id, contactDTO);
        return updatedContact.map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("Contact with ID: {} not found for update", id); // Warn log if not found
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.info("Deleting contact with ID: {}", id);
        boolean isDeleted = contactService.deleteContact(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
