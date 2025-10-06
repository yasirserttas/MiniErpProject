package com.yasir.erp.minierp.modules.cheque.application.service.query;

import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNotFoundException;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNumberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ChequeQueryService implements
        FindChequeByIdAndActiveUseCase,
        FindChequeByNumberAndActiveUseCase,
        ListChequesByActiveUseCase,
        ListChequesByStatusAndActiveUseCase,
        ListChequesByBankAccountAndActiveUseCase,
        ListChequesByCreatedAtBetweenAndActiveUseCase,
        ListChequesByDueDateBeforeStatusActiveUseCase,
        ListChequesByDueDateBetweenStatusActiveUseCase,
        ListChequesByStatusBankAccountAndActiveUseCase {

    private final ChequeQueryPort chequeQueryPort;
    private final ChequeActiveQueryPort chequeActiveQueryPort;
    private final ChequeBankAccountQueryPort chequeBankAccountQueryPort;
    private final ChequeCreatedAtQueryPort chequeCreatedAtQueryPort;
    private final ChequeDueDateQueryPort chequeDueDateQueryPort;
    private final ChequeStatusQueryPort chequeStatusQueryPort;
    private final ChequeConverter chequeConverter;

    public ChequeQueryService(ChequeQueryPort chequeQueryPort,
                              ChequeActiveQueryPort chequeActiveQueryPort,
                              ChequeBankAccountQueryPort chequeBankAccountQueryPort,
                              ChequeCreatedAtQueryPort chequeCreatedAtQueryPort,
                              ChequeDueDateQueryPort chequeDueDateQueryPort,
                              ChequeStatusQueryPort chequeStatusQueryPort,
                              ChequeConverter chequeConverter) {
        this.chequeQueryPort = chequeQueryPort;
        this.chequeActiveQueryPort = chequeActiveQueryPort;
        this.chequeBankAccountQueryPort = chequeBankAccountQueryPort;
        this.chequeCreatedAtQueryPort = chequeCreatedAtQueryPort;
        this.chequeDueDateQueryPort = chequeDueDateQueryPort;
        this.chequeStatusQueryPort = chequeStatusQueryPort;
        this.chequeConverter = chequeConverter;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ChequeDto findChequeByIdAndActive(String id, boolean active) {
        return chequeConverter.convertToChequeDto(findByIdAndActive(id, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ChequeDto findChequeByNumberAndActive(String chequeNumber, boolean active) {
        return chequeConverter.convertToChequeDto
                (findByChequeNumberAndActive(chequeNumber, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByActive(boolean active) {
        return chequeConverter.convertToChequeSetDto(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByStatusAndActive(ChequeStatus status, boolean active) {
        return chequeConverter.convertToChequeSetDto(findAllByStatusAndActive(status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByBankAccountAndActive
            (String bankAccountId, boolean active) {
        return chequeConverter.
                convertToChequeSetDto(findAllByBankAccountAndActive(bankAccountId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return chequeConverter.
                convertToChequeSetDto(findAllByCreatedAtBetweenAndActive(start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByDueDateBeforeStatusActive
            (LocalDateTime now, ChequeStatus status, boolean active) {
        return chequeConverter.convertToChequeSetDto
                (findAllByDueDateBeforeAndStatusAndActive(now, status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByDueDateBetweenStatusActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active) {
        return chequeConverter.convertToChequeSetDto
                (findAllByDueDateBetweenAndStatusAndActive(start, end, status, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ChequeDto> listChequesByStatusBankAccountAndActive
            (ChequeStatus status, String bankAccountId, boolean active) {
        return chequeConverter.convertToChequeSetDto
                (findAllByStatusAndBankAccountIdAndActive(status, bankAccountId, active));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Cheque findByIdAndActive(String id, boolean active) {
        return chequeQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new ChequeNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Cheque findByChequeNumberAndActive(String chequeNumber, boolean active) {
        return chequeQueryPort.findByChequeNumberAndActive(chequeNumber, active)
                .orElseThrow(() -> new ChequeNumberNotFoundException(chequeNumber));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByActive(boolean active) {
        return chequeActiveQueryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByStatusAndActive(ChequeStatus status, boolean active) {
        return chequeStatusQueryPort.findAllByStatusAndActive(status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByBankAccountAndActive(String bankAccountId, boolean active) {
        return chequeBankAccountQueryPort.findAllByBankAccountIdAndActive(bankAccountId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return chequeCreatedAtQueryPort.findAllByCreatedAtBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByDueDateBeforeAndStatusAndActive
            (LocalDateTime now, ChequeStatus status, boolean active) {
        return chequeDueDateQueryPort.
                findAllByDueDateBeforeAndStatusAndActive(now, status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active) {
        return chequeDueDateQueryPort.findAllByDueDateBetweenAndStatusAndActive(start, end, status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Cheque> findAllByStatusAndBankAccountIdAndActive
            (ChequeStatus status, String bankAccountId, boolean active) {
        return chequeStatusQueryPort.
                findAllByStatusAndBankAccountIdAndActive(status, bankAccountId, active);
    }
}
