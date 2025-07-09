package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.model.Category;
import com.kuputhane.bookservice.model.Publisher;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.repository.CategoryRepository;
import com.kuputhane.bookservice.repository.PublisherRepository;
import com.kuputhane.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CategoryRepository categoryRepo;
    private final PublisherRepository publisherRepo;

    @GetMapping
    public ResponseEntity<?> getAllBooksWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = service.getAllBooksPageable(pageable);

        Page<BookDTO> dtoPage = bookPage.map(this::toDto);
        return ResponseEntity.ok(dtoPage);
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
        String categoryName = null;
        String publisherName = null;
        if (b.getCategoryId() != null) {
            categoryName = categoryRepo.findById(b.getCategoryId())
                    .map(Category::getName)
                    .orElse("Bilinmiyor");
        }

        if (b.getPublisherId() != null) {
            publisherName = publisherRepo.findById(b.getPublisherId())
                    .map(Publisher::getName)
                    .orElse("Bilinmiyor");
        }
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
                .categoryName(categoryName)
                .publisherName(publisherName)
                .build();
    }
}
