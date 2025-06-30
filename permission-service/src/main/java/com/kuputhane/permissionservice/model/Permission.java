package com.kuputhane.permissionservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String resource;
    private Integer accessLevel;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Permission parent;

    @Column(name = "role_id")
    private Integer roleId;
}