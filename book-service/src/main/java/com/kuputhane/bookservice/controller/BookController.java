package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    // 🔐 Sadece LIBRARIAN: Geciken kitapları listele
    @GetMapping("/late")
    public ResponseEntity<?> getLateBooks(@RequestHeader("Role") String role) {
        if (!"LIBRARIAN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Yetkisiz erişim.");
        }
        return ResponseEntity.ok(service.getLateBooks());
    }

    // 🔐 Sadece LIBRARIAN: Kitap ödünç ver
    @PostMapping("/lend/{bookId}")
    public ResponseEntity<?> lendBook(@PathVariable Long bookId, @RequestHeader("Role") String role) {
        if (!"LIBRARIAN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sadece librarian ödünç verebilir.");
        }
        return service.lendBook(bookId);
    }

    // 🔓 Herkes: Kitap teslim et
    @PostMapping("/return/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        return service.returnBook(bookId);
    }

    // 🔐 Sadece USER: Teslim tarihini uzat
    @PostMapping("/extend/{bookId}")
    public ResponseEntity<?> extendBook(@PathVariable Long bookId, @RequestHeader("Role") String role) {
        if (!"USER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sadece kullanıcı teslim süresini uzatabilir.");
        }
        return service.extendBook(bookId);
    }
}
