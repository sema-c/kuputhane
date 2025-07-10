package com.kuputhane.paymentservice.repository;

import com.kuputhane.paymentservice.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    long countByReturnedFalse();
}
