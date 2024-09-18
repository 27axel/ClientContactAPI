package com.axel.clientcontactapi.repository;

import com.axel.clientcontactapi.entity.Contact;
import com.axel.clientcontactapi.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByClientIdAndContactType(Long clientId, ContactType contactType);

    List<Contact> findAllByClientId(Long clientId);
}
