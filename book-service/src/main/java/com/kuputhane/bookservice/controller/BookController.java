package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookRepository repo;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        List<BookDTO> dtos = service.getAllBooks()
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(book -> ResponseEntity.ok(toDto(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto) {
        Book b = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .year(dto.getYear())
                .available(true)
                .build();
        Book saved = service.save(b);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(
            @PathVariable Long id,
            @RequestBody BookDTO dto) {
        Book b = Book.builder()
                .id(id)
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .year(dto.getYear())
                .available(dto.getAvailable() != null && dto.getAvailable())
                .dueDate(dto.getDueDate())
                .borrowedBy(dto.getBorrowedBy())
                .returned(dto.getReturned() != null && dto.getReturned())
                .build();
        Book updated = service.save(b);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/late")
    public ResponseEntity<List<BookDTO>> late() {
        List<BookDTO> dtos = service.getLateBooks()
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/lend")
    public ResponseEntity<?> lend(
            @RequestParam Long bookId,
            @RequestParam Long userId) {
        return service.lendBook(bookId, userId);
    }

    @PostMapping("/return")
    public ResponseEntity<?> ret(
            @RequestParam Long bookId) {
        return service.returnBook(bookId);
    }

    @PostMapping("/extend")
    public ResponseEntity<?> extend(
            @RequestParam Long bookId) {
        return service.extendBook(bookId);
    }

    private BookDTO toDto(Book b) {
        return BookDTO.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .year(b.getYear())
                .available(b.isAvailable())
                .dueDate(b.getDueDate())
                .borrowedBy(b.getBorrowedBy())
                .returned(b.isReturned())
                // publisher/category lookup varsa buraya ekleyebilirsin:
                // .categoryName(repo.findCategoryNameById(...))
                // .publisherName(repo.findPublisherNameById(...))
                .build();
    }
}
