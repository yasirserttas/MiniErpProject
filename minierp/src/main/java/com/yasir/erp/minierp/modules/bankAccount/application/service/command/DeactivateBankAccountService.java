package com.yasir.erp.minierp.modules.bankAccount.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.DeactivateBankAccountUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command.BankAccountCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
public class DeactivateBankAccountService implements DeactivateBankAccountUseCase {

    private final BankAccountCommandPort commandPort;
    private final BankAccountByIdAndStatusQueryPort byIdAndStatusPort;
    private final BankAccountConverter converter;

    public DeactivateBankAccountService(BankAccountCommandPort commandPort,
                                        BankAccountByIdAndStatusQueryPort byIdAndStatusPort,
                                        BankAccountConverter converter) {
        this.commandPort = commandPort;
        this.byIdAndStatusPort = byIdAndStatusPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccountDto deactivate(String accountId) {
        BankAccount existing =
                byIdAndStatusPort.findByIdAndStatus(accountId, BankAccountStatus.ACTIVE)
                        .orElseThrow(() -> new BankAccountNotFoundException(accountId));

        BankAccount updated = new BankAccount(
                existing.getId(),
                existing.getBankName(),
                existing.getAccountNumber(),
                existing.getIban(),
                existing.getCurrency(),
                existing.getBalance(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                BankAccountStatus.PASSIVE
        );

        return converter.convertToBankAccountDto(commandPort.save(updated));
    }
}
