package com.kuputhane.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private String username;
    private String role;

    public RegisterResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
