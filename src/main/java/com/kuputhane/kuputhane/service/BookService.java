package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.Book;
import com.kuputhane.kuputhane.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public Book save(Book book) {
        return repo.save(book);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Optional<Book> getById(Long id) {
        return repo.findById(id);
    }
}

