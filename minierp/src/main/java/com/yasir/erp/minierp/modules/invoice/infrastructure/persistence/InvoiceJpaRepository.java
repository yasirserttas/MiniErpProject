package com.yasir.erp.minierp.modules.invoice.infrastructure.persistence;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<Invoice, String> {
    Optional<Invoice> findByIdAndActive(String invoiceId, Boolean active);
    Optional<Invoice> findByOrder_IdAndActive(String orderId, Boolean active);
    Set<Invoice> findAllByOrder_IdAndActive(String orderId, Boolean active);
    Set<Invoice> findAllByCustomer_IdAndActive(UUID customerId, Boolean active);
    Set<Invoice> findAllByUser_IdAndActive(UUID userId, Boolean active);
    Set<Invoice> findAllByCreatedAtBetweenAndActive(LocalDateTime startDate, LocalDateTime endDate, Boolean active);
    Set<Invoice> findAllByFinalAmountGreaterThanEqualAndActive(BigDecimal amount, Boolean active);
    Optional<Invoice> findByInvoiceNumberAndActive(String invoiceNumber, Boolean active);
    Set<Invoice> findAllByCustomer_IdAndCreatedAtBetweenAndActive(UUID customerId, LocalDateTime start, LocalDateTime end, Boolean active);
    Set<Invoice> findAllByIssuedByAndActive(String issuedBy, Boolean active);
    Set<Invoice> findAllByReceivedByAndActive(String receivedBy, Boolean active);
    Set<Invoice> findAllByActiveTrue();
    Set<Invoice> findAllByActiveFalse();

    @Query("""
       SELECT COALESCE(SUM(i.finalAmount), 0)
       FROM Invoice i
       WHERE i.customer.id = :customerId
         AND i.active = true
       """)
    BigDecimal sumTotalByCustomer(@Param("customerId") UUID customerId);

    @Query("""
           select coalesce(sum(i.finalAmount), 0)
           from Invoice i
           where i.customer.id = :customerId
             and i.active = true
           """)
    BigDecimal sumFinalAmountByCustomer(@Param("customerId") UUID customerId);
}
