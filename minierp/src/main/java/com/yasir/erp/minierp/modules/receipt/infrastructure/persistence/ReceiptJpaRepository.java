package com.yasir.erp.minierp.modules.receipt.infrastructure.persistence;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReceiptJpaRepository extends JpaRepository<Receipt, String> {

    Set<Receipt> findAllByActive(Boolean active);
    Set<Receipt> findAllByReceiptDateBetweenAndActive
            (LocalDateTime startDate, LocalDateTime endDate, Boolean active);
    Set<Receipt> findAllByPayment_IdAndActive(String paymentId, Boolean active);
    Set<Receipt> findAllByIssuedByContainingIgnoreCaseAndActive(String issuedBy, Boolean active);
    Set<Receipt> findAllByReceivedByContainingIgnoreCaseAndActive(String receivedBy, Boolean active);
    Set<Receipt> findAllByIssuedByContainingIgnoreCaseAndReceiptDateBetweenAndActive(String issuedBy, LocalDateTime start, LocalDateTime end, Boolean active);

    Optional<Receipt> findByPayment_IdAndActive(String paymentId, Boolean active);
    Optional<Receipt> findByReceiptNumberAndActive(String receiptNumber, Boolean active);
    Optional<Receipt> findByIdAndActive(String receiptId, Boolean active);
}