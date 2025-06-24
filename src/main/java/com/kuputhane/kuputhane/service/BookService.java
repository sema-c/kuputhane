package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book save(Book book);
    void delete(Long id);
    Optional<Book> getById(Long id);
}
