package com.kuputhane.paymentservice.service;

import com.kuputhane.paymentservice.model.Punishment;
import com.kuputhane.paymentservice.repository.PunishmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PunishmentService {

    private final PunishmentRepository punishmentRepository;

    public PunishmentService(PunishmentRepository punishmentRepository) {
        this.punishmentRepository = punishmentRepository;
    }

    public Punishment createPunishment(Long userId, Long bookId, String reason, Double amount) {
        Punishment punishment = Punishment.builder()
                .userId(userId)
                .bookId(bookId)
                .reason(reason)
                .amount(amount)
                .punishmentDate(LocalDate.now())
                .build();
        return punishmentRepository.save(punishment);
    }

    public List<Punishment> getAllPunishments() {
        return punishmentRepository.findAll();
    }

    public void deletePunishment(Long id) {
        punishmentRepository.deleteById(id);
    }
}
