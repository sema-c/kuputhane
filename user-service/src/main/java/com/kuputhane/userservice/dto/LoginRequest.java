package com.kuputhane.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String email;
    private String username;
    private String password;

}
