package com.kuputhane.paymentservice.service;

import com.kuputhane.paymentservice.model.BorrowRecord;
import com.kuputhane.paymentservice.repository.BorrowRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Transactional
    public boolean borrowBook(Long bookId, Long userId) {
        boolean alreadyBorrowed = borrowRecordRepository.findByBookIdAndReturnedFalse(bookId).isPresent();
        if (alreadyBorrowed) {
            return false;
        }

        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setUserId(userId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(15));
        record.setReturned(false);

        borrowRecordRepository.save(record);
        return true;
    }
}
