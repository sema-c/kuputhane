package com.kuputhane.bookservice.service;

import com.kuputhane.bookservice.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book save(Book book);
    void delete(Long id);
    Optional<Book> getById(Long id);
    List<Book> getLateBooks();
    ResponseEntity<?> lendBook(Long bookId, Long userId);
    ResponseEntity<?> returnBook(Long bookId);
    ResponseEntity<?> extendBook(Long bookId);
    Page<Book> getAllBooksPageable(Pageable pageable);
    List<Book> searchBooks(String query);

}
