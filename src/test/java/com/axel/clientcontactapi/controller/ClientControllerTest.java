package com.axel.clientcontactapi.controller;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.service.ClientService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    Client client = new Client();
    List<Client> clients = new ArrayList<>();

    @BeforeEach
    void setUp() {
        client.setId(1L);
        client.setName("test");
        clients.add(client);
    }

    @Test
    void getAllClientsTest() throws Exception {
        JSONObject clientJson = new JSONObject();
        clientJson.put("id", 1L);
        clientJson.put("name", "test");

        when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/api/clients")
                        .content(clientJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService, times(1)).getAllClients();
    }

    @Test
    void getClientByIdTest() throws Exception {
        JSONObject clientJson = new JSONObject();
        clientJson.put("id", 1L);
        clientJson.put("name", "test");

        when(clientService.getClientById(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/clients/1")
                        .content(clientJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService, times(1)).getClientById(any(Long.class));
    }

    @Test
    void createClientTest() throws Exception {
        JSONObject clientJson = new JSONObject();
        clientJson.put("id", 1L);
        clientJson.put("name", "test");

        when(clientService.createClient("test")).thenReturn(new Client());

        mockMvc.perform(post("/api/clients")
                        .content(clientJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(clientService, times(1)).createClient(any(String.class));
    }
}
