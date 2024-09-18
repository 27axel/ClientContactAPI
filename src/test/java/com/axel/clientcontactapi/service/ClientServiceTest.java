package com.axel.clientcontactapi.service;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setName("test");
    }

    @Test
    void testCreateClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client createdClient = clientService.createClient("test");

        assertEquals("test", createdClient.getName());
        assertEquals(1L, createdClient.getId());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = Arrays.asList(client, new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> returnedClients = clientService.getAllClients();

        assertEquals(2, returnedClients.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> returnedClient = clientService.getClientById(1L);

        assertTrue(returnedClient.isPresent());
        assertEquals("test", returnedClient.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }
}
