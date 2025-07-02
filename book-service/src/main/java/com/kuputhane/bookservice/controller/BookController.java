package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.mapper.BookMapper;
import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.model.Category;
import com.kuputhane.bookservice.model.Publisher;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.repository.CategoryRepository;
import com.kuputhane.bookservice.repository.PublisherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:8081")
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookController(BookRepository bookRepository,
                          CategoryRepository categoryRepository,
                          PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookDTO> bookDTOs = bookPage.getContent()
                .stream()
                .map(BookMapper::toDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", bookDTOs);
        response.put("currentPage", bookPage.getNumber());
        response.put("totalItems", bookPage.getTotalElements());
        response.put("totalPages", bookPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String q) {
        List<BookDTO> result = bookRepository.findByTitleContainingIgnoreCase(q)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book book = optionalBook.get();

        String categoryName = categoryRepository.findById((long) book.getCategoryId())
                .map(Category::getName)
                .orElse("Kategori Yok");

        String publisherName = publisherRepository.findById((long) book.getPublisherId())
                .map(Publisher::getName)
                .orElse("Yayınevi Yok");

        BookDTO dto = BookMapper.toDetailedDTO(book, categoryName, publisherName);
        return ResponseEntity.ok(dto);
    }
}
