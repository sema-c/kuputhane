package com.kuputhane.userservice.dto;

import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String surname;
    private String phone;
    private String message;
}
