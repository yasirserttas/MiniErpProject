package com.yasir.erp.minierp.modules.payment.infrastructure.persistence;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, String> {

    Set<Payment> findAllByInvoice_Id(String invoiceId);
    Set<Payment> findAllByMethod(PaymentMethod method);
    Set<Payment> findAllByPaidAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Set<Payment> findAllByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Set<Payment> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Set<Payment> findAllByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Set<Payment> findAllByNoteContainingIgnoreCase(String keyword);
    Set<Payment> findAllByAmountGreaterThanEqual(BigDecimal amount);
    Set<Payment> findAllByAmountLessThanEqual(BigDecimal amount);
    Set<Payment> findAllByAmountBetween(BigDecimal min, BigDecimal max);
    Set<Payment> findAllByInvoice_IdAndMethod(String invoiceId, PaymentMethod method);
    Set<Payment> findAllByMethodAndPaidAtBetween(PaymentMethod method, LocalDateTime start, LocalDateTime end);

    @Query("""
  select coalesce(sum(p.amount), 0)
  from Payment p
  join p.invoice i
  where i.customer.id = :customerId
    and i.active = true
""")
    BigDecimal sumPaidByCustomer(@Param("customerId") UUID customerId);


}
