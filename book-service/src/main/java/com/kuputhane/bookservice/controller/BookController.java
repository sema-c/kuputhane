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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
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
                .location(dto.getLocation())
                .format(dto.getFormat())
                .language(dto.getLanguage())
                .isbn(dto.getIsbn())
                .categoryId(dto.getCategoryId())
                .publisherId(dto.getPublisherId())
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
                .location(dto.getLocation())
                .format(dto.getFormat())
                .language(dto.getLanguage())
                .isbn(dto.getIsbn())
                .categoryId(dto.getCategoryId())
                .publisherId(dto.getPublisherId())
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

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam("q") String query,
            @RequestParam(value = "format",    required = false) String format,
            @RequestParam(value = "language",  required = false) String language,
            @RequestParam(value = "category",  required = false) Long   categoryId,
            @RequestParam(value = "publisher", required = false) Long   publisherId) {

        List<Book> books = service.searchBooks(query, format, language, categoryId, publisherId);
        List<BookDTO> dtos = books.stream().map(this::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/borrowed/{userId}")
    public ResponseEntity<List<BookDTO>> borrowed(@PathVariable Long userId) {
        List<BookDTO> dtos = service.getBorrowedBooks(userId)
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/borrowed/soon-due/{userId}")
    public ResponseEntity<List<BookDTO>> soonDue(@PathVariable Long userId) {
        List<BookDTO> dtos = service.getSoonDueBooks(userId)
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/count")
    public Map<String, Long> getBookCount() {
        long total = service.getAllBooks().size();
        return Map.of("count", total);
    }

    private BookDTO toDto(Book b) {
        String categoryName = null;
        String publisherName = null;
        if (b.getCategoryId() != null) {
            categoryName = categoryRepo.findById(b.getCategoryId())
                    .map(Category::getName).orElse("Bilinmiyor");
        }
        if (b.getPublisherId() != null) {
            publisherName = publisherRepo.findById(b.getPublisherId())
                    .map(Publisher::getName).orElse("Bilinmiyor");
        }
        return BookDTO.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .year(b.getYear())
                .location(b.getLocation())
                .format(b.getFormat())
                .language(b.getLanguage())
                .isbn(b.getIsbn())
                .categoryId(b.getCategoryId())
                .publisherId(b.getPublisherId())
                .available(b.isAvailable())
                .dueDate(b.getDueDate())
                .borrowedBy(b.getBorrowedBy())
                .returned(b.isReturned())
                .categoryName(categoryName)
                .publisherName(publisherName)
                .build();
    }

    @PostMapping("/{id}/cover")
    public ResponseEntity<Void> uploadCover(
            @PathVariable Long id,
            @RequestParam("cover") MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".jpg")) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Path imgDir = Paths.get("src/main/resources/static/img");
        if (!Files.exists(imgDir)) Files.createDirectories(imgDir);
        Path out = imgDir.resolve(id + ".jpg");
        Files.copy(file.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok().build();
    }
}
