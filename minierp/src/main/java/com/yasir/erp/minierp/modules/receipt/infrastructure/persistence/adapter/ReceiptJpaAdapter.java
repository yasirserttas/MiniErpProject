package com.yasir.erp.minierp.modules.receipt.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command.ReceiptCommandPort;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.receipt.infrastructure.persistence.ReceiptJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class ReceiptJpaAdapter implements
        ReceiptCommandPort,
        ReceiptQueryPort,
        ReceiptActiveQueryPort,
        ReceiptDateQueryPort,
        ReceiptTextSearchQueryPort,
        ReceiptPaymentQueryPort {

    private final ReceiptJpaRepository receiptJpaRepository;

    public ReceiptJpaAdapter(ReceiptJpaRepository receiptJpaRepository) {
        this.receiptJpaRepository = receiptJpaRepository;
    }


    @Override public Receipt save(Receipt receipt) {
        return receiptJpaRepository.save(receipt);
    }

    @Override public void deleteById(String id) {
        receiptJpaRepository.deleteById(id);
    }

    @Override public Optional<Receipt> findByIdAndActive(String id, boolean active) {
        return receiptJpaRepository.findByIdAndActive(id, active);
    }

    @Override public Optional<Receipt> findByReceiptNumberAndActive
            (String receiptNumber, boolean active) {
        return receiptJpaRepository.findByReceiptNumberAndActive(receiptNumber, active);
    }

    @Override public Optional<Receipt> findByPaymentIdAndActive(String paymentId, boolean active) {
        return receiptJpaRepository.findByPayment_IdAndActive(paymentId, active);
    }

    @Override public Set<Receipt> findAllByActive(boolean active) {
        return receiptJpaRepository.findAllByActive(active);
    }

    @Override public Set<Receipt> findAll() {
        return Set.copyOf(receiptJpaRepository.findAll());
    }

    @Override public Set<Receipt> findAllByReceiptDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return receiptJpaRepository.findAllByReceiptDateBetweenAndActive(start, end, active);
    }

    @Override public Set<Receipt> findAllByIssuedByAndActive(String issuedBy, boolean active) {
        return receiptJpaRepository.findAllByIssuedByContainingIgnoreCaseAndActive(issuedBy, active);
    }

    @Override public Set<Receipt> findAllByReceivedByAndActive(String receivedBy, boolean active) {
        return receiptJpaRepository.
                findAllByReceivedByContainingIgnoreCaseAndActive(receivedBy, active);
    }

    @Override public Set<Receipt> findAllByIssuedByAndDateBetweenAndActive
            (String issuedBy, LocalDateTime start, LocalDateTime end, boolean active) {
        return receiptJpaRepository.
                findAllByIssuedByContainingIgnoreCaseAndReceiptDateBetweenAndActive
                        (issuedBy, start, end, active);
    }

    @Override public Set<Receipt> findAllByPaymentIdAndActive(String paymentId, boolean active) {
        return receiptJpaRepository.findAllByPayment_IdAndActive(paymentId, active);
    }
}