package com.axel.clientcontactapi.controller;

import com.axel.clientcontactapi.entity.Client;
import com.axel.clientcontactapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client clientResponse = clientService.createClient(client.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }
}
