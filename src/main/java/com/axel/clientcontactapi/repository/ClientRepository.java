package com.axel.clientcontactapi.repository;

import com.axel.clientcontactapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
