package com.kuputhane.bookservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "categories")
public class Category {
    @Getter
    @Setter
    @jakarta.persistence.Id
    @Id
    private Long id;
    private String name;

}