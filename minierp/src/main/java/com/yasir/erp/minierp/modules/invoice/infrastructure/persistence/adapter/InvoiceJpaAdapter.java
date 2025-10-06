package com.yasir.erp.minierp.modules.invoice.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceAmountQueryPort;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command.InvoiceCommandPort;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.invoice.infrastructure.persistence.InvoiceJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component("invoiceJpaAdapter")
public class InvoiceJpaAdapter implements InvoiceCommandPort, InvoiceActiveQueryPort,
        InvoiceAggregationQueryPort, InvoiceAmountQueryPort, InvoiceCreatedAtQueryPort,
        InvoiceCustomerQueryPort,InvoiceIssuedByQueryPort,InvoiceNumberQueryPort ,
InvoiceOrderQueryPort,InvoiceQueryPort,InvoiceReceivedByQueryPort,InvoiceUserQueryPort{

    private final InvoiceJpaRepository invoiceJpaRepository;

    public InvoiceJpaAdapter(InvoiceJpaRepository invoiceJpaRepository) {
        this.invoiceJpaRepository = invoiceJpaRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceJpaRepository.save(invoice);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceJpaRepository.delete(invoice);
    }

    @Override
    public Optional<Invoice> findByIdAndActive(String id, Boolean active) {
        return invoiceJpaRepository.findByIdAndActive(id,active);
    }

    @Override
    public Set<Invoice> findAllByActiveTrue() {
        return invoiceJpaRepository.findAllByActiveTrue();
    }

    @Override
    public Set<Invoice> findAllByActiveFalse() {
        return invoiceJpaRepository.findAllByActiveFalse();
    }

    @Override
    public BigDecimal sumTotalByCustomer(UUID customerId) {
        return invoiceJpaRepository.sumTotalByCustomer(customerId);
    }

    @Override
    public Set<Invoice> findAllByFinalAmountGreaterThanEqualAndActive
            (BigDecimal amount, Boolean active) {
        return invoiceJpaRepository.findAllByFinalAmountGreaterThanEqualAndActive(amount,active);
    }

    @Override
    public Set<Invoice> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active) {
        return invoiceJpaRepository.findAllByCreatedAtBetweenAndActive(start,end,active);
    }

    @Override
    public Set<Invoice> findAllByCustomerIdAndActive(UUID customerId, Boolean active) {
        return invoiceJpaRepository.findAllByCustomer_IdAndActive(customerId, active);
    }

    @Override
    public Set<Invoice> findAllByCustomerIdAndCreatedAtBetweenAndActive
            (UUID customerId, LocalDateTime start, LocalDateTime end, Boolean active) {
        return invoiceJpaRepository.
                findAllByCustomer_IdAndCreatedAtBetweenAndActive(customerId,start,end,active);
    }

    @Override
    public Set<Invoice> findAllByIssuedByAndActive(String issuedBy, Boolean active) {
        return invoiceJpaRepository.findAllByIssuedByAndActive(issuedBy,active);
    }

    @Override
    public Optional<Invoice> findByInvoiceNumberAndActive(String number, Boolean active) {
        return invoiceJpaRepository.findByInvoiceNumberAndActive(number,active);
    }

    @Override
    public Optional<Invoice> findByOrderIdAndActive(String orderId, Boolean active) {
        return invoiceJpaRepository.findByOrder_IdAndActive(orderId,active);
    }

    @Override
    public Set<Invoice> findAllByOrderIdAndActive(String orderId, Boolean active) {
        return invoiceJpaRepository.findAllByOrder_IdAndActive(orderId,active);
    }

    @Override
    public Optional<Invoice> findById(String id) {
        return invoiceJpaRepository.findById(id);
    }

    @Override
    public Set<Invoice> findAllByReceivedByAndActive(String receivedBy, Boolean active) {
        return invoiceJpaRepository.findAllByReceivedByAndActive(receivedBy,active);
    }

    @Override
    public Set<Invoice> findAllByUserIdAndActive(UUID userId, Boolean active) {
        return invoiceJpaRepository.findAllByUser_IdAndActive(userId,active);
    }
}
