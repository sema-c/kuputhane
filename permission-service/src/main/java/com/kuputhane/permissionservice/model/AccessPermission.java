package com.kuputhane.permissionservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String resource;
    private Integer accessLevel;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AccessPermission parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AccessPermission> children = new ArrayList<>();

    @Column(name = "role_id")
    private Integer roleId;
}
