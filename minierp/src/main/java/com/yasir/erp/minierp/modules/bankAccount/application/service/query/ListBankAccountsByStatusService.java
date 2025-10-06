package com.yasir.erp.minierp.modules.bankAccount.application.service.query;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountConverter;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.query.ListBankAccountsByStatusUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountStatusQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ListBankAccountsByStatusService implements ListBankAccountsByStatusUseCase {

    private final BankAccountStatusQueryPort statusPort;
    private final BankAccountConverter converter;

    public ListBankAccountsByStatusService(BankAccountStatusQueryPort statusPort,
                                           BankAccountConverter converter) {
        this.statusPort = statusPort;
        this.converter = converter;
    }

    @Override
    public Set<BankAccountDto> list(BankAccountStatus status) {
        return converter.convertToBankAccountSetDto(statusPort.findAllByStatus(status));
    }
}