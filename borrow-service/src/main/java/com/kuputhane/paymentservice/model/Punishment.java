package com.kuputhane.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "punishments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "punishment_date", nullable = false)
    private LocalDate punishmentDate;
}
