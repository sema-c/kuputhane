package com.kuputhane.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import com.kuputhane.permissionservice.model.AccessPermission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
