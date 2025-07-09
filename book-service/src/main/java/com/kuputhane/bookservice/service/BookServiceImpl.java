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

    // ✅ Geciken kitaplar
    @Override
    public List<Book> getLateBooks() {
        LocalDate today = LocalDate.now();
        return bookRepository.findAll().stream()
                .filter(book -> book.getDueDate() != null && !book.isReturned() && book.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    // ✅ Kitap ödünç ver
    @Override
    public ResponseEntity<?> lendBook(Long bookId, Long userId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();

        if (!book.isAvailable()) {
            return ResponseEntity.badRequest().body("Kitap şu anda ödünçte.");
        }

        book.setAvailable(false);
        book.setReturned(false);
        book.setBorrowedBy(userId);
        book.setDueDate(LocalDate.now().plusDays(14));

        bookRepository.save(book);
        return ResponseEntity.ok("Kitap başarıyla ödünç verildi.");
    }

    @Override
    public ResponseEntity<?> returnBook(Long bookId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();
        book.setAvailable(true);
        book.setReturned(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);

        bookRepository.save(book);
        return ResponseEntity.ok("Kitap iade edildi.");
    }

    @Override
    public ResponseEntity<?> extendBook(Long bookId) {
        Optional<Book> opt = bookRepository.findById(bookId);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Kitap bulunamadı");

        Book book = opt.get();
        if (book.getDueDate() == null) {
            return ResponseEntity.badRequest().body("Bu kitabın teslim tarihi yok.");
        }

        book.setDueDate(book.getDueDate().plusDays(7));
        bookRepository.save(book);
        return ResponseEntity.ok("Teslim süresi uzatıldı.");
    }
}

