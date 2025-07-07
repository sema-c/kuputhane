package com.kuputhane.userservice.dto;

import com.kuputhane.permissionservice.dto.PermissionDTO;
import com.kuputhane.userservice.model.Role;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private List<PermissionDTO> permissions;
}
