package com.axel.clientcontactapi.dto;

import com.axel.clientcontactapi.entity.ContactType;
import lombok.Data;

@Data
public class ContactRequest {
    private ContactType contactType;
    private String contactValue;
}
