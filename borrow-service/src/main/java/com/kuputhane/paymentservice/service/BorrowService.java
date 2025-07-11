package com.kuputhane.paymentservice.service;

public interface BorrowService {
    boolean borrowBook(Long userId, Long bookId);
}
