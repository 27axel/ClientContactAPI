package com.axel.clientcontactapi.controller;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.entity.Contact;
import com.axel.clientcontactapi.entity.ContactType;
import com.axel.clientcontactapi.service.ContactService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    Contact contact = new Contact();
    List<Contact> contacts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        contact.setId(1L);
        contact.setContactType(ContactType.PHONE);
        contact.setContactValue("+7(123)456-78-90");
        contact.setClient(new Client());
        contacts.add(contact);
    }

    @Test
    void getContactsTest() throws Exception {
        JSONObject contactJson = new JSONObject();
        contactJson.put("contactType", ContactType.PHONE);
        contactJson.put("contactValue", "+7(123)456-78-90");

        when(contactService.getContactsByClientId(1L)).thenReturn(contacts);

        mockMvc.perform(get("/api/clients/1/contacts")
                        .content(contactJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(contactService, times(1)).getContactsByClientId(any(Long.class));
    }

    @Test
    void addContactTest() throws Exception {
        JSONObject contactJson = new JSONObject();
        contactJson.put("contactType", ContactType.PHONE);
        contactJson.put("contactValue", "+7(123)456-78-90");

        when(contactService.addContact(1L, ContactType.PHONE, "+7(123)456-78-90")).thenReturn(contact);

        mockMvc.perform(post("/api/clients/1/contacts")
                        .content(contactJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(contactService, times(1)).addContact(any(Long.class), any(ContactType.class), any(String.class));

    }

    @Test
    void getContactTypeTest() throws Exception {
        JSONObject contactJson = new JSONObject();
        contactJson.put("contactType", ContactType.PHONE);
        contactJson.put("contactValue", "+7(123)456-78-90");

        when(contactService.getContactsByType(1L, ContactType.PHONE)).thenReturn(contacts);

        mockMvc.perform(get("/api/clients/1/contacts/type?contactType=PHONE")
                        .content(contactJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(contactService, times(1)).getContactsByType(any(Long.class), any(ContactType.class));
    }
}
