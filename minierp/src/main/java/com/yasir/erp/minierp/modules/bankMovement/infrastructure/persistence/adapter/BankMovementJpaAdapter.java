package com.yasir.erp.minierp.modules.bankMovement.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.command.BankMovementCommandPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankAccountMovementsQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementDateQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementStatusQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.infrastructure.persistence.BankMovementJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class BankMovementJpaAdapter implements BankMovementCommandPort,
        BankMovementQueryPort,
        BankAccountMovementsQueryPort,
        BankMovementDateQueryPort,
        BankMovementStatusQueryPort {

    private final BankMovementJpaRepository bankMovementJpaRepository;

    public BankMovementJpaAdapter(BankMovementJpaRepository bankMovementJpaRepository) {
        this.bankMovementJpaRepository = bankMovementJpaRepository;
    }

    @Override
    public BankMovement save(BankMovement m) {
        return bankMovementJpaRepository.save(m);
    }

    @Override
    public void delete(BankMovement m) {
        bankMovementJpaRepository.delete(m);
    }

    @Override
    public Set<BankMovement> findAllByBankAccountId(String bankAccountId) {
        return bankMovementJpaRepository.findAllByBankAccount_Id(bankAccountId);
    }

    @Override
    public Set<BankMovement> findAllByBankAccountIdAndStatus(String bankAccountId, BankMovementStatus status) {
        return bankMovementJpaRepository.findAllByBankAccount_IdAndBankMovementStatus(bankAccountId, status);
    }

    @Override
    public Set<BankMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return bankMovementJpaRepository.findAllByDateBetween(start, end);
    }

    @Override
    public Set<BankMovement> findAllByDateBetweenAndStatus(LocalDateTime start, LocalDateTime end, BankMovementStatus status) {
        return bankMovementJpaRepository.findAllByDateBetweenAndBankMovementStatus(start, end, status);
    }

    @Override
    public Set<BankMovement> findAllByBankAccountIdAndDateBetween(String bankAccountId, LocalDateTime start, LocalDateTime end) {
        return bankMovementJpaRepository.findAllByBankAccount_IdAndDateBetween(bankAccountId, start, end);
    }

    @Override
    public Set<BankMovement> findAllByBankAccountIdAndDateBetweenAndStatus(String bankAccountId, LocalDateTime start, LocalDateTime end, BankMovementStatus status) {
        return bankMovementJpaRepository.findAllByBankAccount_IdAndDateBetweenAndBankMovementStatus(bankAccountId, start, end, status);
    }

    @Override
    public Optional<BankMovement> findById(String id) {
        return bankMovementJpaRepository.findById(id);
    }

    @Override
    public Set<BankMovement> findAllByStatus(BankMovementStatus status) {
        return bankMovementJpaRepository.findAllByBankMovementStatus(status);
    }
}
