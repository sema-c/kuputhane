package com.kuputhane.paymentservice.service;

import com.kuputhane.paymentservice.model.Payment;
import com.kuputhane.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return repository.findById(id);
    }

    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
