package com.yasir.erp.minierp.modules.dispatchNote.infrastructure.persistence;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DispatchNoteJpaRepository extends JpaRepository<DispatchNote, String> {

    boolean existsByDispatchNoteNumber(String dispatchNoteNumber);

    Set<DispatchNote> findAllByCustomerIdAndActive(UUID customerId, boolean active);
    Set<DispatchNote> findAllByOrderIdAndActive(String orderId, boolean active);
    Set<DispatchNote> findAllByUserIdAndActive(UUID userId, boolean active);

    Set<DispatchNote> findAllByDispatchDateBetweenAndActive
    (LocalDateTime start, LocalDateTime end, boolean active);

    Set<DispatchNote> findAllByDispatchNoteStatusAndActive
    (DispatchNoteStatus status, boolean active);
    Set<DispatchNote> findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(
            DispatchNoteStatus status, LocalDateTime start, LocalDateTime end, boolean active);

    Optional<DispatchNote> findByCustomerIdAndActive(UUID customerId, boolean active);
    Optional<DispatchNote> findByOrderIdAndActive(String orderId, boolean active);
    Optional<DispatchNote> findByUserIdAndActive(UUID userId, boolean active);
    Optional<DispatchNote> findByIdAndActive(String dispatchNoteId, boolean active);
    Optional<DispatchNote> findByDispatchNoteNumberAndActive
            (String dispatchNoteNumber, boolean active);
}
