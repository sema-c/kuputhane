package com.kuputhane.bookservice.service;

import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
import com.kuputhane.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

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
        return bookRepository.findByDueDateBeforeAndReturnedFalse(LocalDate.now());
    }

    @Override
    @Transactional
    public ResponseEntity<?> lendBook(Long bookId, Long userId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.badRequest().body("Kitap bulunamadı.");
        }

        Book book = optionalBook.get();

        if (!book.isAvailable()) {
            return ResponseEntity.badRequest().body("Kitap şu anda ödünç alınamaz.");
        }

        book.setBorrowedBy(userId);
        book.setReturned(false);
        book.setAvailable(false);
        book.setBorrowDate(LocalDate.now());
        book.setDueDate(LocalDate.now().plusDays(14));

        bookRepository.save(book);

        return ResponseEntity.ok("Kitap başarıyla ödünç verildi.");
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.badRequest().body("Kitap bulunamadı.");
        }

        Book book = optionalBook.get();

        if (book.isReturned()) {
            return ResponseEntity.badRequest().body("Kitap zaten iade edilmiş.");
        }

        book.setReturned(true);
        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowDate(null);
        book.setDueDate(null);

        bookRepository.save(book);

        return ResponseEntity.ok("Kitap başarıyla iade edildi.");
    }

    @Override
    @Transactional
    public ResponseEntity<?> extendBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.badRequest().body("Kitap bulunamadı.");
        }

        Book book = optionalBook.get();

        if (book.getDueDate() == null || book.isReturned()) {
            return ResponseEntity.badRequest().body("Teslim tarihi uzatılamaz.");
        }

        book.setDueDate(book.getDueDate().plusDays(7)); // 7 gün uzatma
        bookRepository.save(book);

        return ResponseEntity.ok("Teslim tarihi uzatıldı.");
    }

    @Override
    public Page<Book> getAllBooksPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> searchBooks(String q,
                                  String format,
                                  String language,
                                  Long categoryId,
                                  Long publisherId) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q.toLowerCase()))
                .filter(b -> format       == null || format.isBlank()    || b.getFormat().equalsIgnoreCase(format))
                .filter(b -> language     == null || language.isBlank()  || b.getLanguage().equalsIgnoreCase(language))
                .filter(b -> categoryId   == null                      || categoryId.equals(b.getCategoryId()))
                .filter(b -> publisherId  == null                      || publisherId.equals(b.getPublisherId()))
                .toList();
    }

    @Override
    public List<Book> getBorrowedBooks(Long userId) {
        return bookRepository.findByBorrowedByAndReturnedFalse(userId);
    }

    @Override
    public List<Book> getSoonDueBooks(Long userId) {
        return bookRepository.findSoonDueBooks(userId, LocalDate.now().plusDays(3));
    }


}

