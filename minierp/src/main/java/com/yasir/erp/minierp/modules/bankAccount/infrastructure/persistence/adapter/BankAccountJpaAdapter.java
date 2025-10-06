package com.yasir.erp.minierp.modules.bankAccount.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command.BankAccountCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.bankAccount.infrastructure.persistence.BankAccountJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class BankAccountJpaAdapter implements BankAccountCommandPort, BankAccountBalanceQueryPort
, BankAccountBankNameQueryPort, BankAccountCreatedAtQueryPort, BankAccountExistsQueryPort,
        BankAccountIbanQueryPort,BankAccountQueryPort,BankAccountStatusQueryPort ,
        BankAccountByIdAndStatusQueryPort{

    private final BankAccountJpaRepository bankAccountJpaRepository;

    public BankAccountJpaAdapter(BankAccountJpaRepository bankAccountJpaRepository) {
        this.bankAccountJpaRepository = bankAccountJpaRepository;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return bankAccountJpaRepository.save(bankAccount);
    }

    @Override
    public void delete(BankAccount bankAccount) {
        bankAccountJpaRepository.delete(bankAccount);
    }

    @Override
    public Set<BankAccount> findAllByBalanceBetween(BigDecimal min, BigDecimal max) {
        return bankAccountJpaRepository.findAllByBalanceBetween(min,max);
    }

    @Override
    public Set<BankAccount> findAllByBankName(String bankName) {
        return bankAccountJpaRepository.findAllByBankName(bankName);
    }

    @Override
    public Set<BankAccount> findAllByBankNameAndStatus(String bankName, BankAccountStatus status) {
        return bankAccountJpaRepository.findAllByBankNameAndStatus(bankName,status);
    }

    @Override
    public Set<BankAccount> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return bankAccountJpaRepository.findAllByCreatedAtBetween(start,end);
    }

    @Override
    public Set<BankAccount> findAllByStatusAndCreatedAtBetween
            (BankAccountStatus status, LocalDateTime start, LocalDateTime end) {
        return bankAccountJpaRepository.findAllByStatusAndCreatedAtBetween(status,start,end);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return bankAccountJpaRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public Optional<BankAccount> findByIban(String iban) {
        return bankAccountJpaRepository.findByIban(iban);
    }

    @Override
    public Optional<BankAccount> findByIdAndStatus(String id, BankAccountStatus status) {
        return bankAccountJpaRepository.findByIdAndStatus(id,status);
    }

    @Override
    public Set<BankAccount> findAllByStatus(BankAccountStatus status) {
        return bankAccountJpaRepository.findAllByStatus(status);
    }
}
