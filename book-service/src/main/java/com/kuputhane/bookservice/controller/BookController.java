package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.dto.BookDTO;
import com.kuputhane.bookservice.mapper.BookMapper;
import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:8081")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        List<BookDTO> result = bookRepository.findByTitleContainingIgnoreCase(q).stream()
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

        String categoryName = bookRepository.findCategoryNameById(Long.valueOf(book.getCategoryId()));
        String publisherName = bookRepository.findPublisherNameById(Long.valueOf(book.getPublisherId()));

        BookDTO dto = BookMapper.toDetailedDTO(book, categoryName, publisherName);
        return ResponseEntity.ok(dto);
    }


}
