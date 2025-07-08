package com.kuputhane.paymentservice.service;

import com.kuputhane.paymentservice.dto.BookDTO;
import com.kuputhane.paymentservice.model.BorrowRecord;
import com.kuputhane.paymentservice.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRecordRepository borrowRepository;
    private final RestTemplate restTemplate;

    public BorrowServiceImpl(BorrowRecordRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public boolean borrowBook(Long userId, Long bookId) {
        String bookUrl = "http://localhost:8082/api/books/" + bookId;
        BookDTO book = restTemplate.getForObject(bookUrl, BookDTO.class);

        if (book == null || !book.isAvailable()) return false;

        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturned(false);
        record.setPunishmentApplied(false);

        borrowRepository.save(record);

        String updateUrl = "http://localhost:8082/api/books/" + bookId + "/availability?available=false";
        restTemplate.put(updateUrl, null);

        return true;
    }
}
