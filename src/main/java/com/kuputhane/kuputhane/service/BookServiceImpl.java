package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.Book;
import com.kuputhane.kuputhane.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    @Override
    public Book save(Book book) {
        return repo.save(book);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return repo.findById(id);
    }
}
