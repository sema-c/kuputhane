package com.kuputhane.kuputhane.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passwordHash;   // veritabanına kaydedilen hash

}
