package com.bridgelabz.addressBookApp.service;


import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.model.Contact;
import com.bridgelabz.addressBookApp.repository.ContactRepository;
import com.bridgelabz.addressBookApp.mapper.ContactMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.bridgelabz.addressBookApp.exceptions.AddressBookException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class ContactService implements IContactService {

    ContactRepository contactRepository;
    ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Cacheable(value = "contacts", key = "'allContacts'")
    public List<ContactDTO> getAllContacts() {
        try {
            return contactRepository.findAll()
                    .stream()
                    .map(contactMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AddressBookException("An error occurred while fetching contacts: " + e.getMessage());
        }
    }


    public ContactDTO getContactById(Long id) {
        try {
            String key = "contact_" + id;

            // Check if contact exists in Redis
            if (redisTemplate.hasKey(key)) {
                return (ContactDTO) redisTemplate.opsForValue().get(key);
            }

            Optional<Contact> contact = contactRepository.findById(id);
            if (contact.isEmpty()) {
                throw new AddressBookException("Contact with ID: " + id + " not found");
            }

            ContactDTO contactDTO = contactMapper.toDTO(contact.get());

            // Store in Redis with 10-minute expiry
            redisTemplate.opsForValue().set(key, contactDTO, 10, TimeUnit.MINUTES);

            return contactDTO;
        } catch (Exception e) {
            throw new AddressBookException("Error retrieving contact with ID " + id + ": " + e.getMessage());
        }
    }


    @CacheEvict(value = "contacts", key = "'allContacts'")
    public ContactDTO addContact(ContactDTO contactDTO) {
        try {
            Contact contact = contactMapper.toEntity(contactDTO);
            ContactDTO savedContact = contactMapper.toDTO(contactRepository.save(contact));

            // Add to Redis
            redisTemplate.opsForValue().set("contact_" + savedContact.getId(), savedContact, 10, TimeUnit.MINUTES);

            return savedContact;
        } catch (Exception e) {
            throw new AddressBookException("Error adding new contact: " + e.getMessage());
        }
    }


    @CacheEvict(value = "contacts", key = "'allContacts'")
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        try {
            Optional<Contact> existingContact = contactRepository.findById(id);
            if (existingContact.isEmpty()) {
                throw new AddressBookException("Contact with ID: " + id + " not found for update");
            }

            existingContact.get().setName(contactDTO.getName());
            existingContact.get().setEmail(contactDTO.getEmail());
            existingContact.get().setPhone(contactDTO.getPhone());
            ContactDTO updatedContact = contactMapper.toDTO(contactRepository.save(existingContact.get()));

            // Remove old cache and add updated contact to Redis
            String key = "contact_" + id;
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(key, updatedContact, 10, TimeUnit.MINUTES);

            return updatedContact;
        } catch (Exception e) {
            throw new AddressBookException("Error updating contact with ID " + id + ": " + e.getMessage());
        }
    }


    @CacheEvict(value = "contacts", key = "'allContacts'")
    public boolean deleteContact(Long id) {
        try {
            if (contactRepository.existsById(id)) {
                contactRepository.deleteById(id);

                // Remove contact from Redis
                redisTemplate.delete("contact_" + id);

                return true;
            } else {
                throw new AddressBookException("Contact with ID: " + id + " not found for deletion");
            }
        } catch (Exception e) {
            throw new AddressBookException("Error deleting contact with ID " + id + ": " + e.getMessage());
        }
    }

}