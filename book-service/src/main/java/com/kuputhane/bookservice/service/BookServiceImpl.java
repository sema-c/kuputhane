package com.kuputhane.bookservice.service;

import com.kuputhane.bookservice.model.Book;
import com.kuputhane.bookservice.repository.BookRepository;
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

    private final BookRepository repo;

    @Override
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    // ✅ EKLENDİ: Sayfalı kitapları döndür
    @Override
    public Page<Book> getAllBooksPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() != null) {
            Book existing = repo.findById(book.getId())
                    .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setYear(book.getYear());
            existing.setAvailable(book.isAvailable());
            existing.setDueDate(book.getDueDate());
            existing.setBorrowedBy(book.getBorrowedBy());
            existing.setReturned(book.isReturned());
            return repo.save(existing);
        } else {
            book.setYear(LocalDate.now().getYear());
            book.setAvailable(true);
            book.setReturned(false);
            return repo.save(book);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Book> getLateBooks() {
        return repo.findByDueDateBeforeAndReturnedFalse(LocalDate.now());
    }

    @Override
    @Transactional
    public ResponseEntity<?> lendBook(Long bookId, Long userId) {
        return repo.findById(bookId)
                .map(book -> {
                    if (!book.isAvailable()) {
                        return ResponseEntity.badRequest()
                                .body("Kitap şu anda müsait değil.");
                    }
                    book.setAvailable(false);
                    book.setBorrowedBy(userId);
                    book.setDueDate(LocalDate.now().plusWeeks(2));
                    repo.save(book);
                    return ResponseEntity.ok(
                            "Kitap ödünç verildi. Son teslim: " + book.getDueDate()
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnBook(Long bookId) {
        return repo.findById(bookId)
                .map(book -> {
                    if (book.isReturned() || book.isAvailable()) {
                        return ResponseEntity.badRequest()
                                .body("Bu kitap ödünç verilmemiş veya zaten iade edilmiş.");
                    }
                    book.setReturned(true);
                    book.setAvailable(true);
                    repo.save(book);
                    return ResponseEntity.ok("Kitap başarıyla iade edildi.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> extendBook(Long bookId) {
        return repo.findById(bookId)
                .map(book -> {
                    if (book.isAvailable() || book.isReturned()) {
                        return ResponseEntity.badRequest()
                                .body("Bu kitap şu anda ödünçte değil.");
                    }
                    book.setDueDate(book.getDueDate().plusWeeks(1));
                    repo.save(book);
                    return ResponseEntity.ok(
                            "Teslim tarihi uzatıldı: " + book.getDueDate()
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
