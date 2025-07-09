package com.kuputhane.bookservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "publishers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Publisher {
    @Id
    @Column(name = "publisher_id")
    private Long id;
    private String name;
}
