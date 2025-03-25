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
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setCity(contact.getCity());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setState(contact.getState());
        contactDTO.setZipCode(contact.getZipCode());
        return contactDTO;
    }

    public Contact toEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setName(contactDTO.getName());
        contact.setPhone(contactDTO.getPhone());
        contact.setCity(contactDTO.getCity());
        contact.setAddress(contactDTO.getAddress());
        contact.setState(contactDTO.getState());
        contact.setZipCode(contactDTO.getZipCode());
        return contact;
    }
}