package com.kuputhane.bookservice.service;

import com.kuputhane.bookservice.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book save(Book book);
    void delete(Long id);
    Optional<Book> getById(Long id);
}
