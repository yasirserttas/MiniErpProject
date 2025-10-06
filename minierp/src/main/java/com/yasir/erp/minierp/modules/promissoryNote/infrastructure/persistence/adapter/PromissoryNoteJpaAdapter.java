package com.yasir.erp.minierp.modules.promissoryNote.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.promissoryNote.infrastructure.persistence.PromissoryNoteJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class PromissoryNoteJpaAdapter implements
        PromissoryNoteCommandPort,
        PromissoryNoteQueryPort,
        PromissoryNoteActiveQueryPort,
        PromissoryNoteStatusQueryPort,
        PromissoryNoteBankAccountQueryPort,
        PromissoryNoteDueDateQueryPort,
        PromissoryNoteCreatedAtQueryPort,
        PromissoryNoteUpdatedAtQueryPort,
        PromissoryNoteDebtorQueryPort,
        PromissoryNoteAmountQueryPort {

    private final PromissoryNoteJpaRepository promissoryNoteJpaRepository;

    public PromissoryNoteJpaAdapter(PromissoryNoteJpaRepository promissoryNoteJpaRepository) {
        this.promissoryNoteJpaRepository = promissoryNoteJpaRepository;
    }

    @Override public PromissoryNote save(PromissoryNote promissoryNote) {
        return promissoryNoteJpaRepository.save(promissoryNote);
    }

    @Override public Optional<PromissoryNote> findByIdAndActive(String id, boolean active){
        return promissoryNoteJpaRepository.findByIdAndActive(id, active);
    }

    @Override public Optional<PromissoryNote> findByNoteNumberAndActive
            (String noteNumber, boolean active){
        return promissoryNoteJpaRepository.findByNoteNumberAndActive(noteNumber, active);
    }

    @Override public Set<PromissoryNote> findAllByActive(boolean active){
        return promissoryNoteJpaRepository.findAllByActive(active);
    }

    @Override public Set<PromissoryNote> findAllByStatusAndActive
            (PromissoryNoteStatus status, boolean active){
        return promissoryNoteJpaRepository.findAllByStatusAndActive(status, active);
    }

    @Override public Set<PromissoryNote> findAllByBankAccountIdAndActive
            (String bankAccountId, boolean active){
        return promissoryNoteJpaRepository.findAllByBankAccount_IdAndActive(bankAccountId, active);
    }

    @Override public Set<PromissoryNote> findAllByDueDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active){
        return promissoryNoteJpaRepository.findAllByDueDateBetweenAndActive(start, end, active);
    }

    @Override public Set<PromissoryNote> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, PromissoryNoteStatus status, boolean active){
        return promissoryNoteJpaRepository.
                findAllByDueDateBetweenAndStatusAndActive(start, end, status, active);
    }

    @Override public Set<PromissoryNote> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active){
        return promissoryNoteJpaRepository.findAllByCreatedAtBetweenAndActive(start, end, active);
    }

    @Override public Set<PromissoryNote> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active){
        return promissoryNoteJpaRepository.findAllByUpdatedAtBetweenAndActive(start, end, active);
    }

    @Override public Set<PromissoryNote> findAllByDebtorAndActive(String debtor, boolean active) {
        return promissoryNoteJpaRepository.findAllByDebtorAndActive(debtor, active);
    }

    @Override public Set<PromissoryNote> findAllByAmountBetweenAndActive
            (BigDecimal min, BigDecimal max, boolean active){
        return promissoryNoteJpaRepository.findAllByAmountBetweenAndActive(min, max, active);
    }
}