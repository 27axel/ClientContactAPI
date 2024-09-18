package com.axel.clientcontactapi.service;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.entity.Contact;
import com.axel.clientcontactapi.entity.ContactType;
import com.axel.clientcontactapi.repository.ClientRepository;
import com.axel.clientcontactapi.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;

    public ContactService(ContactRepository contactRepository, ClientRepository clientRepository) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
    }

    public Contact addContact(Long clientId, ContactType contactType, String contactValue) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Contact contact = new Contact();
        contact.setClient(client);
        contact.setContactType(contactType);
        contact.setContactValue(contactValue);
        return contactRepository.save(contact);
    }

    public List<Contact> getContactsByClientId(Long clientId) {
        return contactRepository.findAllByClientId(clientId);
    }

    public List<Contact> getContactsByType(Long clientId, ContactType contactType) {
        return contactRepository.findByClientIdAndContactType(clientId, contactType);
    }

}
