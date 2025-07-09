package com.kuputhane.paymentservice.controller;

import com.kuputhane.paymentservice.dto.BorrowRequest;
import com.kuputhane.paymentservice.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public boolean borrowBook(@RequestBody BorrowRequest request) {
        return borrowService.borrowBook(request.getUserId(), request.getBookId());
    }
}
