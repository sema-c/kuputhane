package com.kuputhane.paymentservice.controller;

import com.kuputhane.paymentservice.dto.BorrowRequest;
import com.kuputhane.paymentservice.repository.BorrowRecordRepository;
import com.kuputhane.paymentservice.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*")
public class BorrowController {

    private final BorrowService borrowService;
    private final BorrowRecordRepository borrowRepository;

    @Autowired
    public BorrowController(BorrowService borrowService, BorrowRecordRepository borrowRepository) {
        this.borrowService = borrowService;
        this.borrowRepository = borrowRepository;
    }

    @PostMapping
    public boolean borrowBook(@RequestBody BorrowRequest request) {
        return borrowService.borrowBook(request.getUserId(), request.getBookId());
    }
}
