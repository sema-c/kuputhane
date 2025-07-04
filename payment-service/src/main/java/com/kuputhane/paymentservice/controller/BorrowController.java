package com.kuputhane.paymentservice.controller;

import com.kuputhane.paymentservice.model.BorrowRecord;
import com.kuputhane.paymentservice.service.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@CrossOrigin(origins = "*")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRecord record) {
        try {
            boolean success = borrowService.borrowBook(record.getUserId(), record.getBookId());
            if (success) {
                return ResponseEntity.ok("Kitap ödünç alındı.");
            } else {
                return ResponseEntity.badRequest().body("Kitap zaten ödünçte.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Bir hata oluştu.");
        }
    }
}
