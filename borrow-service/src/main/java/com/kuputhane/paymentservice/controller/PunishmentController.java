package com.kuputhane.paymentservice.controller;

import com.kuputhane.paymentservice.model.Punishment;
import com.kuputhane.paymentservice.service.PunishmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/punishments")
@CrossOrigin(origins = "*")
public class PunishmentController {

    private final PunishmentService punishmentService;

    public PunishmentController(PunishmentService punishmentService) {
        this.punishmentService = punishmentService;
    }

    @PostMapping
    public ResponseEntity<Punishment> createPunishment(@RequestBody Punishment punishment) {
        Punishment created = punishmentService.createPunishment(
                punishment.getUserId(),
                punishment.getBookId(),
                punishment.getReason(),
                punishment.getAmount()
        );
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Punishment>> getAllPunishments() {
        return ResponseEntity.ok(punishmentService.getAllPunishments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePunishment(@PathVariable Long id) {
        punishmentService.deletePunishment(id);
        return ResponseEntity.noContent().build();
    }
}
