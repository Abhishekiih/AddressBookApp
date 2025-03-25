package com.bridgelabz.addressBookApp.controller;

import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.service.IContactService;
import lombok.extern.slf4j.Slf4j;
import com.bridgelabz.addressBookApp.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/contacts")
public class ContactController {

    private final IContactService contactService;
    private final MessageProducer messageProducer;

    @Autowired
    public ContactController(IContactService contactService, MessageProducer messageProducer) {
        this.contactService = contactService;
        this.messageProducer = messageProducer;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Fetching all contacts");
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        ContactDTO contactDTO = contactService.getContactById(id);
        return ResponseEntity.ok(contactDTO);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO);
        ContactDTO savedContact = contactService.addContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO) {
        log.info("Updating contact with ID: {}", id);
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        return ResponseEntity.ok(updatedContact);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        log.info("Deleting contact with ID: {}", id);
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok("Contact deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting contact", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting contact");
        }
    }

    @PostMapping("/sendToQueue")
    public ResponseEntity<String> sendToQueue(@RequestBody ContactDTO dto) {
        messageProducer.sendMessage("Contact Info: " + dto);
        return ResponseEntity.ok("Contact sent to RabbitMQ successfully");
    }
}
