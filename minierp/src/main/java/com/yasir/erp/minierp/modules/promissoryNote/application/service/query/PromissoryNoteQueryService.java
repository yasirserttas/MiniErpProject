package com.yasir.erp.minierp.modules.promissoryNote.application.service.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.promissoryNote.application.exception.PromissoryNoteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PromissoryNoteQueryService implements
        FindPromissoryNoteByIdUseCase,
        FindPromissoryNoteByNoteNumberUseCase,
        ListPromissoryNotesByActiveUseCase,
        ListPromissoryNotesByAmountBetweenUseCase,
        ListPromissoryNotesByBankAccountUseCase,
        ListPromissoryNotesByCreatedAtBetweenUseCase,
        ListPromissoryNotesByDebtorUseCase,
        ListPromissoryNotesByDueDateAndStatusUseCase,
        ListPromissoryNotesByDueDateBetweenUseCase,
        ListPromissoryNotesByStatusUseCase,
        ListPromissoryNotesByUpdatedAtBetweenUseCase {

    private final PromissoryNoteConverter converter;
    private final PromissoryNoteQueryPort baseQueryPort;
    private final PromissoryNoteActiveQueryPort activeQueryPort;
    private final PromissoryNoteStatusQueryPort statusQueryPort;
    private final PromissoryNoteBankAccountQueryPort bankAccountQueryPort;
    private final PromissoryNoteDebtorQueryPort debtorQueryPort;
    private final PromissoryNoteAmountQueryPort amountQueryPort;
    private final PromissoryNoteDueDateQueryPort dueDateQueryPort;
    private final PromissoryNoteCreatedAtQueryPort createdAtQueryPort;
    private final PromissoryNoteUpdatedAtQueryPort updatedAtQueryPort;

    public PromissoryNoteQueryService(
            PromissoryNoteConverter converter,
            PromissoryNoteQueryPort baseQueryPort,
            PromissoryNoteActiveQueryPort activeQueryPort,
            PromissoryNoteStatusQueryPort statusQueryPort,
            PromissoryNoteBankAccountQueryPort bankAccountQueryPort,
            PromissoryNoteDebtorQueryPort debtorQueryPort,
            PromissoryNoteAmountQueryPort amountQueryPort,
            PromissoryNoteDueDateQueryPort dueDateQueryPort,
            PromissoryNoteCreatedAtQueryPort createdAtQueryPort,
            PromissoryNoteUpdatedAtQueryPort updatedAtQueryPort
    ) {
        this.converter = converter;
        this.baseQueryPort = baseQueryPort;
        this.activeQueryPort = activeQueryPort;
        this.statusQueryPort = statusQueryPort;
        this.bankAccountQueryPort = bankAccountQueryPort;
        this.debtorQueryPort = debtorQueryPort;
        this.amountQueryPort = amountQueryPort;
        this.dueDateQueryPort = dueDateQueryPort;
        this.createdAtQueryPort = createdAtQueryPort;
        this.updatedAtQueryPort = updatedAtQueryPort;
    }

    

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PromissoryNoteDto findDtoByIdAndActive(String promissoryNoteId, boolean active) {
        return converter.convertToPromissoryNoteDto(findByIdAndActive(promissoryNoteId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PromissoryNoteDto findDtoByNoteNumberAndActive(String noteNumber, boolean active) {
        return converter.convertToPromissoryNoteDto(findByNoteNumberAndActive(noteNumber, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByActive(boolean active) {
        return converter.convertToPromissoryNoteSetDto(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByAmountBetweenAndActive
            (BigDecimal minAmount, BigDecimal maxAmount, boolean active) {
        return converter.
                convertToPromissoryNoteSetDto
                        (findAllByAmountBetweenAndActive(minAmount, maxAmount, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByBankAccountIdAndActive
            (String bankAccountId, boolean active) {
        return converter.
                convertToPromissoryNoteSetDto
                        (findAllByBankAccountIdAndActive(bankAccountId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.
                convertToPromissoryNoteSetDto
                        (findAllByCreatedAtBetweenAndActive(start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByDebtorAndActive(String debtor, boolean active) {
        return converter.convertToPromissoryNoteSetDto(findAllByDebtorAndActive(debtor, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end,
             PromissoryNoteStatus status, boolean active) {
        return converter.convertToPromissoryNoteSetDto
                (findAllByDueDateBetweenAndStatusAndActive(start, end, status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByDueDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToPromissoryNoteSetDto
                (findAllByDueDateBetweenAndActive(start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByStatusAndActive
            (PromissoryNoteStatus status, boolean active) {
        return converter.convertToPromissoryNoteSetDto
                (findAllByStatusAndActive(status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PromissoryNoteDto> findDtoAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToPromissoryNoteSetDto
                (findAllByUpdatedAtBetweenAndActive(start, end, active));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected PromissoryNote findByIdAndActive(String id, boolean active) {
        return baseQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new PromissoryNoteNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected PromissoryNote findByNoteNumberAndActive(String noteNumber, boolean active) {
        return baseQueryPort.findByNoteNumberAndActive(noteNumber, active)
                .orElseThrow(() -> new PromissoryNoteNotFoundException(noteNumber));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByActive(boolean active) {
        return activeQueryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByAmountBetweenAndActive
            (BigDecimal min, BigDecimal max, boolean active) {
        return amountQueryPort.findAllByAmountBetweenAndActive(min, max, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByBankAccountIdAndActive
            (String bankAccountId, boolean active) {
        return bankAccountQueryPort.findAllByBankAccountIdAndActive(bankAccountId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return createdAtQueryPort.findAllByCreatedAtBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByDebtorAndActive(String debtor, boolean active) {
        return debtorQueryPort.findAllByDebtorAndActive(debtor, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end,
             PromissoryNoteStatus status, boolean active) {
        return dueDateQueryPort.
                findAllByDueDateBetweenAndStatusAndActive(start, end, status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByDueDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return dueDateQueryPort.findAllByDueDateBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByStatusAndActive
            (PromissoryNoteStatus status, boolean active) {
        return statusQueryPort.findAllByStatusAndActive(status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<PromissoryNote> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return updatedAtQueryPort.findAllByUpdatedAtBetweenAndActive(start, end, active);
    }
}
