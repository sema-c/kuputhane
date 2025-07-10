package com.kuputhane.bookservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Integer year;

    private boolean available;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private Long borrowedBy;
    private boolean returned;
    private Long categoryId;
    private Long publisherId;

}
