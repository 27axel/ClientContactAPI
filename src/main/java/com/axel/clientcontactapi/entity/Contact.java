package com.axel.clientcontactapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
}
