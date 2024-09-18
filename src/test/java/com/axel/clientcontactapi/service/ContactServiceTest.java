package com.axel.clientcontactapi.service;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.entity.Contact;
import com.axel.clientcontactapi.entity.ContactType;
import com.axel.clientcontactapi.repository.ClientRepository;
import com.axel.clientcontactapi.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ContactService contactService;

    private Client client;
    private Contact contact;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setName("test");

        contact = new Contact();
        contact.setId(1L);
        contact.setClient(client);
        contact.setContactType(ContactType.PHONE);
        contact.setContactValue("+7(123)456-78-90");
    }

    @Test
    void testAddContact() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact addedContact = contactService.addContact(1L, ContactType.PHONE, "+7(123)456-78-90");

        assertEquals(ContactType.PHONE, addedContact.getContactType());
        assertEquals("+7(123)456-78-90", addedContact.getContactValue());
        assertEquals(1L, addedContact.getClient().getId());
        verify(clientRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testAddContactClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                contactService.addContact(1L, ContactType.PHONE, "+7(123)456-78-90"));

        assertEquals("Client not found", thrown.getMessage());
        verify(clientRepository, times(1)).findById(1L);
        verify(contactRepository, times(0)).save(any(Contact.class));
    }

    @Test
    void testGetContactsByClientId() {
        List<Contact> contacts = Arrays.asList(contact, new Contact());
        when(contactRepository.findAllByClientId(1L)).thenReturn(contacts);

        List<Contact> returnedContacts = contactService.getContactsByClientId(1L);

        assertEquals(2, returnedContacts.size());
        assertEquals(contact.getContactValue(), returnedContacts.get(0).getContactValue());
        verify(contactRepository, times(1)).findAllByClientId(1L);
    }

    @Test
    void testGetContactsByType() {
        List<Contact> contacts = Arrays.asList(contact);
        when(contactRepository.findByClientIdAndContactType(1L, ContactType.PHONE)).thenReturn(contacts);

        List<Contact> returnedContacts = contactService.getContactsByType(1L, ContactType.PHONE);

        assertEquals(1, returnedContacts.size());
        assertEquals(contact.getContactValue(), returnedContacts.get(0).getContactValue());
        verify(contactRepository, times(1)).findByClientIdAndContactType(1L, ContactType.PHONE);
    }
}
