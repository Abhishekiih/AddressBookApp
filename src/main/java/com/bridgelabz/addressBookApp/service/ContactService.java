package com.bridgelabz.addressBookApp.service;


import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.exceptions.AddressBookException;
import com.bridgelabz.addressBookApp.model.Contact;
import com.bridgelabz.addressBookApp.repository.ContactRepository;
import com.bridgelabz.addressBookApp.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    // ===================== GET ALL CONTACTS =====================
    @Override
    @Cacheable(value = "contacts")
    public List<ContactDTO> getAllContacts() {
        try {
            return contactRepository.findAll().stream()
                    .map(contactMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AddressBookException("Error fetching all contacts: " + e.getMessage());
        }
    }

    // ===================== GET CONTACT BY ID =====================
    @Override
    @Cacheable(value = "contacts", key = "#id")
    public ContactDTO getContactById(Long id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            return contact.map(contactMapper::toDTO)
                    .orElseThrow(() -> new AddressBookException("Contact not found with ID: " + id));
        } catch (Exception e) {
            throw new AddressBookException("Error fetching contact with ID " + id + ": " + e.getMessage());
        }
    }

    // ===================== ADD CONTACT =====================
    @Override
    @CachePut(value = "contacts", key = "#result.id")
    public ContactDTO addContact(ContactDTO contactDTO) {
        try {
            Contact contact = contactMapper.toEntity(contactDTO);
            return contactMapper.toDTO(contactRepository.save(contact));
        } catch (Exception e) {
            throw new AddressBookException("Error adding contact: " + e.getMessage());
        }
    }

    // ===================== UPDATE CONTACT =====================
    @Override
    @CachePut(value = "contacts", key = "#id")
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        try {
            Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new AddressBookException("Contact not found for update with ID: " + id));

            contact.setName(contactDTO.getName());
//            contact.setEmail(contactDTO.getEmail());
            contact.setPhone(contactDTO.getPhone());
            contact.setCity(contactDTO.getCity());

            // Ensure address, state, zipCode are updated
            contact.setAddress(contactDTO.getAddress());
            contact.setState(contactDTO.getState());
            contact.setZipCode(contactDTO.getZipCode());

            return contactMapper.toDTO(contactRepository.save(contact));
        } catch (Exception e) {
            throw new AddressBookException("Error updating contact with ID " + id + ": " + e.getMessage());
        }
    }

    // ===================== DELETE CONTACT =====================
    @Override
    @CacheEvict(value = "contacts", key = "#id")
    public boolean deleteContact(Long id) {
        try {
            if (contactRepository.existsById(id)) {
                contactRepository.deleteById(id);
                return true;
            } else {
                throw new AddressBookException("Contact not found for deletion with ID: " + id);
            }
        } catch (Exception e) {
            throw new AddressBookException("Error deleting contact with ID " + id + ": " + e.getMessage());
        }
    }
}