package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.mapper.BookMapper;
import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService service;
    private final BookRepository bookRepository;

    public BookController(BookService service, BookRepository bookRepository) {
        this.service = service;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAll() {
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String q) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(q);
        List<BookDTO> result = books.stream()
                .map(BookMapper::toDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

}
