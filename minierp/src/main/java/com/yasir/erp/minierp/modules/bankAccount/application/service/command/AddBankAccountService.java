package com.yasir.erp.minierp.modules.bankAccount.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.request.CreateBankAccountDtoRequest;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.AddBankAccountUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command.BankAccountCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountExistsQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountIbanQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.DuplicateBankAccountIbanException;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.DuplicateBankAccountNumberException;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddBankAccountService implements AddBankAccountUseCase {

    private final BankAccountCommandPort commandPort;
    private final BankAccountExistsQueryPort existsPort;
    private final BankAccountIbanQueryPort ibanPort;
    private final BankAccountConverter converter;

    public AddBankAccountService(BankAccountCommandPort commandPort,
                                 BankAccountExistsQueryPort existsPort,
                                 BankAccountIbanQueryPort ibanPort,
                                 BankAccountConverter converter) {
        this.commandPort = commandPort;
        this.existsPort = existsPort;
        this.ibanPort = ibanPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccountDto addBankAccount(CreateBankAccountDtoRequest createBankAccountDtoRequest) {
        if (existsPort.existsByAccountNumber(createBankAccountDtoRequest.getAccountNumber())) {
            throw new DuplicateBankAccountNumberException
                    (createBankAccountDtoRequest.getAccountNumber());
        }
        if (ibanPort.findByIban(createBankAccountDtoRequest.getIban()).isPresent()) {
            throw new DuplicateBankAccountIbanException(createBankAccountDtoRequest.getIban());
        }

        BankAccount a = new BankAccount(
                UlidGenerator.generate(),
                createBankAccountDtoRequest.getBankName(),
                createBankAccountDtoRequest.getAccountNumber(),
                createBankAccountDtoRequest.getIban(),
                createBankAccountDtoRequest.getCurrency(),
                createBankAccountDtoRequest.getCurrentBalance(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                BankAccountStatus.ACTIVE
        );
        return converter.convertToBankAccountDto(commandPort.save(a));
    }
}
