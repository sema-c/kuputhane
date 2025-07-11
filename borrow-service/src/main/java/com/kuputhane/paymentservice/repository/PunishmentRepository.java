package com.kuputhane.paymentservice.repository;

import com.kuputhane.paymentservice.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunishmentRepository extends JpaRepository<Punishment, Long> {
}
