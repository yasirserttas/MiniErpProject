package com.yasir.erp.minierp.modules.bankAccount.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.UpdateBankAccountBalanceUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command.BankAccountCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.InsufficientBalanceException;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UpdateBankAccountBalanceService implements UpdateBankAccountBalanceUseCase {

    private final BankAccountCommandPort commandPort;
    private final BankAccountByIdAndStatusQueryPort byIdAndStatusPort;
    private final BankAccountConverter converter;

    public UpdateBankAccountBalanceService(BankAccountCommandPort commandPort,
                                           BankAccountByIdAndStatusQueryPort byIdAndStatusPort,
                                           BankAccountConverter converter) {
        this.commandPort = commandPort;
        this.byIdAndStatusPort = byIdAndStatusPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccountDto updateBalance
            (String bankAccountId, BigDecimal amount, BankMovementType type) {
        BankAccount existing =
                byIdAndStatusPort.findByIdAndStatus(bankAccountId, BankAccountStatus.ACTIVE)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        BigDecimal current = existing.getBalance();
        BigDecimal next;
        if (type == BankMovementType.INCOME) {
            next = current.add(amount);
        } else if (type == BankMovementType.OUTCOME) {
            if (current.compareTo(amount) < 0) {
                throw new InsufficientBalanceException(bankAccountId, amount);
            }
            next = current.subtract(amount);
        } else {
            throw new IllegalArgumentException("Invalid BankMovementType: " + type);
        }

        BankAccount updated = new BankAccount(
                existing.getId(), existing.getBankName(),
                existing.getAccountNumber(), existing.getIban(),
                existing.getCurrency(), next, existing.getCreatedAt(),
                LocalDateTime.now(), BankAccountStatus.ACTIVE
        );

        return converter.convertToBankAccountDto(commandPort.save(updated));
    }
}
