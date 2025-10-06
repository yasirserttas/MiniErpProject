package com.yasir.erp.minierp.modules.bankAccount.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.request.UpdateBankAccountDtoRequest;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.UpdateBankAccountUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command.BankAccountCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountExistsQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountIbanQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.DuplicateBankAccountIbanException;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.DuplicateBankAccountNumberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateBankAccountService implements UpdateBankAccountUseCase {

    private final BankAccountCommandPort commandPort;
    private final BankAccountByIdAndStatusQueryPort byIdAndStatusPort;
    private final BankAccountExistsQueryPort existsPort;
    private final BankAccountIbanQueryPort ibanPort;
    private final BankAccountConverter converter;

    public UpdateBankAccountService(BankAccountCommandPort commandPort,
                                    BankAccountByIdAndStatusQueryPort byIdAndStatusPort,
                                    BankAccountExistsQueryPort existsPort,
                                    BankAccountIbanQueryPort ibanPort,
                                    BankAccountConverter converter) {
        this.commandPort = commandPort;
        this.byIdAndStatusPort = byIdAndStatusPort;
        this.existsPort = existsPort;
        this.ibanPort = ibanPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccountDto update(UpdateBankAccountDtoRequest updateBankAccountDtoRequest) {
        BankAccount existing = byIdAndStatusPort
                .findByIdAndStatus
                        (updateBankAccountDtoRequest.getBankAccountId(), BankAccountStatus.ACTIVE)
                .orElseThrow(() -> new
                        BankAccountNotFoundException
                        (updateBankAccountDtoRequest.getBankAccountId()));

        if (!existing.getAccountNumber().equals(updateBankAccountDtoRequest.getAccountNumber())
                && existsPort.existsByAccountNumber(updateBankAccountDtoRequest.getAccountNumber())) {
            throw new DuplicateBankAccountNumberException(updateBankAccountDtoRequest.getAccountNumber());
        }

        if (!existing.getIban().equals(updateBankAccountDtoRequest.getIban())
                && ibanPort.findByIban(updateBankAccountDtoRequest.getIban()).isPresent()) {
            throw new DuplicateBankAccountIbanException(updateBankAccountDtoRequest.getIban());
        }

        BankAccount updated = new BankAccount(
                existing.getId(),
                updateBankAccountDtoRequest.getBankName(),
                updateBankAccountDtoRequest.getAccountNumber(),
                updateBankAccountDtoRequest.getIban(),
                updateBankAccountDtoRequest.getCurrency(),
                existing.getBalance(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                BankAccountStatus.ACTIVE
        );

        return converter.convertToBankAccountDto(commandPort.save(updated));
    }
}