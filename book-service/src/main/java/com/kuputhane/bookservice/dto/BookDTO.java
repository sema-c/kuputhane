package com.kuputhane.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private String location;
    private String format;
    private String language;
    private String isbn;
    private Long categoryId;
    private Long publisherId;
    private Boolean available;
    private LocalDate dueDate;
    private Long borrowedBy;
    private Boolean returned;
    private String categoryName;
    private String publisherName;
}
