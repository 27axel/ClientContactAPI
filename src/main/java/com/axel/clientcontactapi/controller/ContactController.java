package com.axel.clientcontactapi.controller;

import com.axel.clientcontactapi.dto.ContactRequest;
import com.axel.clientcontactapi.entity.Contact;
import com.axel.clientcontactapi.entity.ContactType;
import com.axel.clientcontactapi.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients/{clientId}/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@PathVariable Long clientId, @RequestBody ContactRequest contactRequest) {
        return new ResponseEntity<>(
                contactService.addContact(clientId, contactRequest.getContactType(), contactRequest.getContactValue()),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getContacts(@PathVariable Long clientId) {
        List<Contact> contacts = contactService.getContactsByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Contact>> getContactType(@PathVariable Long clientId, @RequestParam ContactType contactType) {
        List<Contact> contacts = contactService.getContactsByType(clientId, contactType);
        return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }

}
