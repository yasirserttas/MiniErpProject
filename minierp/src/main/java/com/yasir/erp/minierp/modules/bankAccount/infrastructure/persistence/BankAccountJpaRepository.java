package com.yasir.erp.minierp.modules.bankAccount.infrastructure.persistence;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BankAccountJpaRepository extends JpaRepository<BankAccount,String> {
    Optional<BankAccount> findByIdAndStatus(String id, BankAccountStatus status);
    Set<BankAccount> findAllByStatus(BankAccountStatus status);
    Optional<BankAccount> findByIban(String iban);
    boolean existsByAccountNumber(String accountNumber);
    Set<BankAccount> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Set<BankAccount> findAllByStatusAndCreatedAtBetween(
            BankAccountStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    Set<BankAccount> findAllByBalanceBetween(BigDecimal min, BigDecimal max);
    Set<BankAccount> findAllByBankName(String bankName);
    Set<BankAccount> findAllByBankNameAndStatus(String bankName, BankAccountStatus status);
}
