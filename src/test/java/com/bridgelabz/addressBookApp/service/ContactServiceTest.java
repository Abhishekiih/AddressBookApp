package com.bridgelabz.addressBookApp.service;

import com.bridgelabz.addressBookApp.dto.ContactDTO;
import com.bridgelabz.addressBookApp.exceptions.AddressBookException;
import com.bridgelabz.addressBookApp.mapper.ContactMapper;
import com.bridgelabz.addressBookApp.model.Contact;
import com.bridgelabz.addressBookApp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contact = new Contact(1L, "John Doe", "john@example.com", "1234567890", "New York");
        contactDTO = new ContactDTO(1L, "John Doe", "john@example.com", "1234567890", "New York");
    }

    // ===================== GET ALL CONTACTS =====================
    @Test
    void getAllContacts_ShouldReturnContactList() {
        List<Contact> contactList = Arrays.asList(contact);
        when(contactRepository.findAll()).thenReturn(contactList);
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        List<ContactDTO> result = contactService.getAllContacts();
        assertEquals(1, result.size());
        assertEquals(contactDTO, result.get(0));

        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void getAllContacts_ShouldThrowExceptionWhenErrorOccurs() {
        when(contactRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.getAllContacts();
        });
        assertTrue(exception.getMessage().contains("Error fetching contacts"));
    }

    // ===================== GET CONTACT BY ID =====================
    @Test
    void getContactById_ShouldReturnContact() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        ContactDTO result = contactService.getContactById(1L);
        assertNotNull(result);
        assertEquals(contactDTO, result);

        verify(contactRepository, times(1)).findById(1L);
    }

    @Test
    void getContactById_ShouldThrowExceptionWhenContactNotFound() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.getContactById(1L);
        });
        assertTrue(exception.getMessage().contains("Contact not found with ID"));
    }

    // ===================== ADD CONTACT =====================
    @Test
    void addContact_ShouldAddAndReturnContact() {
        when(contactMapper.toEntity(contactDTO)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        ContactDTO result = contactService.addContact(contactDTO);
        assertNotNull(result);
        assertEquals(contactDTO, result);

        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void addContact_ShouldThrowExceptionWhenErrorOccurs() {
        when(contactMapper.toEntity(contactDTO)).thenReturn(contact);
        when(contactRepository.save(contact)).thenThrow(new RuntimeException("DB Error"));

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.addContact(contactDTO);
        });
        assertTrue(exception.getMessage().contains("Error adding contact"));
    }

    // ===================== UPDATE CONTACT =====================
    @Test
    void updateContact_ShouldUpdateAndReturnContact() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        ContactDTO result = contactService.updateContact(1L, contactDTO);
        assertNotNull(result);
        assertEquals(contactDTO, result);

        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void updateContact_ShouldThrowExceptionWhenContactNotFound() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.updateContact(1L, contactDTO);
        });
        assertTrue(exception.getMessage().contains("Contact not found for update with ID"));
    }

    // ===================== DELETE CONTACT =====================
    @Test
    void deleteContact_ShouldDeleteContactWhenExists() {
        when(contactRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contactRepository).deleteById(1L);

        boolean result = contactService.deleteContact(1L);
        assertTrue(result);

        verify(contactRepository, times(1)).existsById(1L);
        verify(contactRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteContact_ShouldThrowExceptionWhenContactNotFound() {
        when(contactRepository.existsById(1L)).thenReturn(false);

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.deleteContact(1L);
        });
        assertTrue(exception.getMessage().contains("Contact not found for deletion with ID"));
    }

    @Test
    void deleteContact_ShouldThrowExceptionWhenErrorOccurs() {
        when(contactRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Delete error")).when(contactRepository).deleteById(1L);

        AddressBookException exception = assertThrows(AddressBookException.class, () -> {
            contactService.deleteContact(1L);
        });
        assertTrue(exception.getMessage().contains("Error deleting contact with ID"));
    }
}

