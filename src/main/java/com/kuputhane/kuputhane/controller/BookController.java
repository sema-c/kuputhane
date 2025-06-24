package com.kuputhane.kuputhane.controller;

import com.kuputhane.kuputhane.model.Book;
import com.kuputhane.kuputhane.repository.BookRepository;
import com.kuputhane.kuputhane.service.BookService;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/books")
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
    public Book getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String q) {
        return bookRepository.findByTitleContainingIgnoreCase(q);
    }

    @PostMapping
    public Book save(@RequestBody Book book) {
        return service.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
