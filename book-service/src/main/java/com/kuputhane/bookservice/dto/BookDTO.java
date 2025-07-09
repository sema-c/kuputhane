package com.kuputhane.bookservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private Integer year;
    private String categoryName;
    private String publisherName;
    private Boolean available;
}
