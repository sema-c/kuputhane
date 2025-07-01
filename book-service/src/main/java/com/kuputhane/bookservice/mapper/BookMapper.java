package com.kuputhane.bookservice.mapper;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.model.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear()
        );
    }
}

