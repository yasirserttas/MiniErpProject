package com.yasir.erp.minierp.modules.bankMovement.application.service.query;

import com.yasir.erp.minierp.modules.bankMovement.application.converter.BankMovementConverter;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankAccountMovementsQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementDateQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementStatusQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.application.exception.BankMovementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class BankMovementQueryService implements
        FindBankMovementByIdUseCase,
        ListActiveBankMovementsUseCase,
        ListAllBankMovementsUseCase,
        ListBankMovementsByBankAccountAndDateBetweenAndStatusUseCase,
        ListBankMovementsByBankAccountAndDateBetweenUseCase,
        ListBankMovementsByBankAccountAndStatusUseCase,
        ListBankMovementsByBankAccountUseCase,
        ListBankMovementsByDateBetweenAndStatusUseCase,
        ListBankMovementsByDateBetweenUseCase,
        ListBankMovementsByStatusUseCase,
        ListCancelledBankMovementsUseCase {

    private final BankMovementQueryPort movementQueryPort;
    private final BankMovementStatusQueryPort statusQueryPort;
    private final BankMovementDateQueryPort dateQueryPort;
    private final BankAccountMovementsQueryPort accountMovementsQueryPort;
    private final BankMovementConverter converter;

    public BankMovementQueryService(BankMovementQueryPort movementQueryPort,
                                    BankMovementStatusQueryPort statusQueryPort,
                                    BankMovementDateQueryPort dateQueryPort,
                                    BankAccountMovementsQueryPort accountMovementsQueryPort,
                                    BankMovementConverter converter) {
        this.movementQueryPort = movementQueryPort;
        this.statusQueryPort = statusQueryPort;
        this.dateQueryPort = dateQueryPort;
        this.accountMovementsQueryPort = accountMovementsQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BankMovementDto findBankMovementById(String id) {
        return converter.convertToBankMovementDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listActiveBankMovement() {
        return converter.convertToBankMovementSetDto(findAllByStatus(BankMovementStatus.ACTIVE));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByBankAccountAndDateBetweenAndStatus(
            String bankAccountId, LocalDateTime start,
            LocalDateTime end, BankMovementStatus status) {
        return converter.convertToBankMovementSetDto(
                findAllByBankAccountIdAndDateBetweenAndStatus(bankAccountId, start, end, status)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByBankAccountAndDateBetween(
            String bankAccountId, LocalDateTime start, LocalDateTime end) {
        return converter.convertToBankMovementSetDto(
                findAllByBankAccountIdAndDateBetween(bankAccountId, start, end)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByBankAccountAndStatus(
            String bankAccountId, BankMovementStatus status) {
        return converter.convertToBankMovementSetDto(
                findAllByBankAccountIdAndStatus(bankAccountId, status)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByBankAccount(String bankAccountId) {
        return converter.convertToBankMovementSetDto(
                findAllByBankAccountId(bankAccountId)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByDateBetweenAndStatus(
            LocalDateTime start, LocalDateTime end, BankMovementStatus status) {
        return converter.convertToBankMovementSetDto(
                findAllByDateBetweenAndStatus(start, end, status)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByDateBetween
            (LocalDateTime start, LocalDateTime end) {
        return converter.convertToBankMovementSetDto(
                findAllByDateBetween(start, end)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listBankMovementsByStatus(BankMovementStatus status) {
        return converter.convertToBankMovementSetDto(findAllByStatus(status));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<BankMovementDto> listCancelledBankMovement() {
        return converter.convertToBankMovementSetDto
                (findAllByStatus(BankMovementStatus.CANCELLED));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected BankMovement findById(String id) {
        return movementQueryPort.findById(id)
                .orElseThrow(() -> new BankMovementNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByStatus(BankMovementStatus status) {
        return statusQueryPort.findAllByStatus(status);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByBankAccountIdAndDateBetweenAndStatus(
            String bankAccountId, LocalDateTime start, LocalDateTime end,
            BankMovementStatus status) {
        return dateQueryPort.findAllByBankAccountIdAndDateBetweenAndStatus
                (bankAccountId, start, end, status);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByBankAccountIdAndDateBetween(
            String bankAccountId, LocalDateTime start, LocalDateTime end) {
        return dateQueryPort.findAllByBankAccountIdAndDateBetween(bankAccountId, start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByBankAccountIdAndStatus
            (String bankAccountId, BankMovementStatus status) {
        return accountMovementsQueryPort.findAllByBankAccountIdAndStatus(bankAccountId, status);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByBankAccountId(String bankAccountId) {
        return accountMovementsQueryPort.findAllByBankAccountId(bankAccountId);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByDateBetweenAndStatus
            (LocalDateTime start, LocalDateTime end, BankMovementStatus status) {
        return dateQueryPort.findAllByDateBetweenAndStatus(start, end, status);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<BankMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return dateQueryPort.findAllByDateBetween(start, end);
    }
}
