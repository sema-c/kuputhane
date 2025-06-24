package com.kuputhane.kuputhane.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private String location;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "publisher_id")
    private Integer publisherId;

}
