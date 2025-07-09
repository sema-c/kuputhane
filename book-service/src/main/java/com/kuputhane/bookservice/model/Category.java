package com.kuputhane.bookservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    @Id
    @Column(name = "category_id")
    private Long id;
    private String name;
}
