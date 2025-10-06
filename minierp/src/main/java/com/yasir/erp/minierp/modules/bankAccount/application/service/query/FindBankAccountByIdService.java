package com.yasir.erp.minierp.modules.bankAccount.application.service.query;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.query.FindBankAccountByIdUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindBankAccountByIdService implements FindBankAccountByIdUseCase {

    private final BankAccountByIdAndStatusQueryPort byIdAndStatusPort;
    private final BankAccountConverter converter;

    public FindBankAccountByIdService(BankAccountByIdAndStatusQueryPort byIdAndStatusPort,
                                      BankAccountConverter converter) {
        this.byIdAndStatusPort = byIdAndStatusPort;
        this.converter = converter;
    }

    @Override
    public BankAccountDto find(String bankAccountId, BankAccountStatus status) {
        return converter.convertToBankAccountDto(
                byIdAndStatusPort.findByIdAndStatus(bankAccountId, status)
                        .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId))
        );
    }
}
