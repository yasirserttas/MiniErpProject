package com.yasir.erp.minierp.modules.bankMovement.infrastructure.persistence;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface BankMovementJpaRepository extends JpaRepository<BankMovement, String> {

    Set<BankMovement> findAllByBankAccount_Id(String bankAccountId);
    Set<BankMovement> findAllByBankMovementStatus(BankMovementStatus status);

    Set<BankMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

    Set<BankMovement> findAllByBankAccount_IdAndDateBetween(String bankAccountId,
                                                            LocalDateTime start,
                                                            LocalDateTime end);

    Set<BankMovement> findAllByBankAccount_IdAndDateBetweenAndBankMovementStatus(
            String bankAccountId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            BankMovementStatus status
    );

    Set<BankMovement> findAllByDateBetweenAndBankMovementStatus(
            LocalDateTime startDate,
            LocalDateTime endDate,
            BankMovementStatus status
    );

    Set<BankMovement> findAllByBankAccount_IdAndBankMovementStatus(
            String bankAccountId,
            BankMovementStatus status
    );
}