package com.kuputhane.bookservice.service;

import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getLateBooks() {
        List<Book> allBooks = bookRepository.findAll();
        LocalDate today = LocalDate.now();

        return allBooks.stream()
                .filter(book -> book.getDueDate() != null && book.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> lendBook(Long bookId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();
        book.setAvailable(true);
        book.setDueDate(LocalDate.now().plusDays(14)); // 2 hafta ödünç
        bookRepository.save(book);

        return ResponseEntity.ok("Kitap ödünç verildi.");
    }

    @Override
    public ResponseEntity<?> returnBook(Long bookId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();
        book.setAvailable(false);
        book.setDueDate(null);
        bookRepository.save(book);

        return ResponseEntity.ok("Kitap teslim edildi.");
    }

    @Override
    public ResponseEntity<?> extendBook(Long bookId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();
        book.setDueDate(book.getDueDate().plusDays(7)); // +7 gün
        bookRepository.save(book);

        return ResponseEntity.ok("Teslim süresi uzatıldı.");
    }
}
