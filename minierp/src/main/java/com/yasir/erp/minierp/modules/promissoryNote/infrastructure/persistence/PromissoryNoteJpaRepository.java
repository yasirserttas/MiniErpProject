package com.yasir.erp.minierp.modules.promissoryNote.infrastructure.persistence;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PromissoryNoteJpaRepository extends JpaRepository<PromissoryNote, String> {

    boolean existsByNoteNumber(String noteNumber);

    Set<PromissoryNote> findAllByActive(boolean active);
    Set<PromissoryNote> findAllByStatusAndActive(PromissoryNoteStatus status, boolean active);
    Set<PromissoryNote> findAllByBankAccount_IdAndActive(String bankAccountId, boolean active);

    Set<PromissoryNote> findAllByDueDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<PromissoryNote> findAllByDueDateBetweenAndStatusAndActive(
            LocalDateTime start, LocalDateTime end, PromissoryNoteStatus status, boolean active);

    Set<PromissoryNote> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<PromissoryNote> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);

    Set<PromissoryNote> findAllByDebtorAndActive(String debtor, boolean active);
    Set<PromissoryNote> findAllByAmountBetweenAndActive
            (BigDecimal minAmount, BigDecimal maxAmount, boolean active);

    Optional<PromissoryNote> findByIdAndActive(String id, boolean active);
    Optional<PromissoryNote> findByNoteNumberAndActive(String noteNumber, boolean active);
}
