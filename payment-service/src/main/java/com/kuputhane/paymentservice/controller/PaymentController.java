package com.kuputhane.paymentservice.controller;
import com.kuputhane.paymentservice.model.Payment;
import com.kuputhane.paymentservice.repository.PaymentRepository;
import com.kuputhane.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService service;
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService service, PaymentRepository paymentRepository) {
        this.service = service;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(service.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
