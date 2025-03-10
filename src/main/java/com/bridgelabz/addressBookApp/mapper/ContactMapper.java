package com.bridgelabz.addressBookApp.mapper;

import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setName(contact.getName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setCity(contact.getCity());
        return contactDTO;
    }

    public Contact toEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact.setCity(contactDTO.getCity());
        return contact;
    }
}