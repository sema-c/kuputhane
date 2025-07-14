package com.kuputhane.bookservice.mapper;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.model.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setYear(book.getYear());
        dto.setAvailable(book.isAvailable());
        return dto;
    }

    public static BookDTO toDetailedDTO(Book book, String categoryName, String publisherName) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setYear(book.getYear());
        dto.setAvailable(book.isAvailable());
        dto.setCategoryName(categoryName);
        dto.setPublisherName(publisherName);
        return dto;
    }
}

