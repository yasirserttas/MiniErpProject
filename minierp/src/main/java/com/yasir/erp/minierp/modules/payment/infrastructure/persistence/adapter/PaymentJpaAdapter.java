package com.yasir.erp.minierp.modules.payment.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.command.PaymentCommandPort;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.payment.infrastructure.persistence.PaymentJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class PaymentJpaAdapter implements
        PaymentCommandPort,
        PaymentQueryPort,
        PaymentInvoiceQueryPort,
        PaymentMethodQueryPort,
        PaymentDateRangeQueryPort,
        PaymentNoteQueryPort,
        PaymentAmountQueryPort,
        PaymentAggregatePort {

    private final PaymentJpaRepository paymentJpaRepository;

    public PaymentJpaAdapter(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository; }

    // command
    @Override public Payment save(Payment p) {
        return paymentJpaRepository.save(p);
    }

    @Override public void delete(Payment p) {
        paymentJpaRepository.delete(p);
    }

    @Override public Optional<Payment> findById(String id) {
        return paymentJpaRepository.findById(id);
    }

    @Override public Set<Payment> findAllByInvoiceId(String invoiceId) {
        return paymentJpaRepository.findAllByInvoice_Id(invoiceId);
    }

    @Override public Set<Payment> findAllByMethod(PaymentMethod method) {
        return paymentJpaRepository.findAllByMethod(method);
    }

    @Override public Set<Payment> findAllByInvoiceIdAndMethod(String invoiceId, PaymentMethod method)
    { return paymentJpaRepository.findAllByInvoice_IdAndMethod(invoiceId, method);
    }

    @Override public Set<Payment> findAllByMethodAndPaidAtBetween
            (PaymentMethod method, LocalDateTime start, LocalDateTime end) {
        return paymentJpaRepository.findAllByMethodAndPaidAtBetween(method, start, end);
    }

    @Override public Set<Payment> findAllByPaidAtBetween(LocalDateTime s, LocalDateTime e) {
        return paymentJpaRepository.findAllByPaidAtBetween(s, e);
    }

    @Override public Set<Payment> findAllByDueDateBetween(LocalDateTime s, LocalDateTime e) {
        return paymentJpaRepository.findAllByDueDateBetween(s, e);
    }

    @Override public Set<Payment> findAllByCreatedAtBetween(LocalDateTime s, LocalDateTime e) {
        return paymentJpaRepository.findAllByCreatedAtBetween(s, e);
    }

    @Override public Set<Payment> findAllByUpdatedAtBetween(LocalDateTime s, LocalDateTime e) {
        return paymentJpaRepository.findAllByUpdatedAtBetween(s, e);
    }

    @Override public Set<Payment> findAllByNoteContainingIgnoreCase(String keyword) {
        return paymentJpaRepository.findAllByNoteContainingIgnoreCase(keyword);
    }

    @Override public Set<Payment> findAllByAmountGreaterThanEqual(BigDecimal amount) {
        return paymentJpaRepository.findAllByAmountGreaterThanEqual(amount);
    }

    @Override public Set<Payment> findAllByAmountLessThanEqual(BigDecimal amount) {
        return paymentJpaRepository.findAllByAmountLessThanEqual(amount);
    }

    @Override public Set<Payment> findAllByAmountBetween(BigDecimal min, BigDecimal max) {
        return paymentJpaRepository.findAllByAmountBetween(min, max);
    }

    @Override public BigDecimal sumPaidByCustomer(UUID customerId) {
        return paymentJpaRepository.sumPaidByCustomer(customerId);
    }
}
