package com.kuputhane.bookservice.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private Integer year;

    // Constructor
    public BookDTO(Long id, String title, String author, Integer year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    // Getter + Setter (İstersen Lombok da kullanabilirsin)
    // @Getter @Setter @AllArgsConstructor @NoArgsConstructor
}

