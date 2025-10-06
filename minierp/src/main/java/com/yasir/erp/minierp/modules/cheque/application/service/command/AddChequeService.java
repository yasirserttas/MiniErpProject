package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.application.dto.request.CreateChequeDtoRequest;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.AddChequeUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeExistsQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNumberAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddChequeService implements AddChequeUseCase {

    private final ChequeCommandPort commandPort;
    private final ChequeExistsQueryPort existsPort;
    private final BankAccountByIdAndStatusQueryPort bankAccountByIdAndStatusPort;
    private final ChequeConverter converter;

    public AddChequeService(ChequeCommandPort commandPort,
                            ChequeExistsQueryPort existsPort,
                            BankAccountByIdAndStatusQueryPort
                                    bankAccountByIdAndStatusPort,
                            ChequeConverter converter) {
        this.commandPort = commandPort;
        this.existsPort = existsPort;
        this.bankAccountByIdAndStatusPort = bankAccountByIdAndStatusPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChequeDto add(CreateChequeDtoRequest req) {

        if (existsPort.existsByChequeNumber(req.getChequeNumber())) {
            throw new ChequeNumberAlreadyExistsException(req.getChequeNumber());
        }

        BankAccount bankAccount = bankAccountByIdAndStatusPort
                .findByIdAndStatus(req.getBankAccountId(), BankAccountStatus.ACTIVE)
                .orElseThrow(() -> new IllegalStateException
                        ("BankAccount not ACTIVE: " + req.getBankAccountId()));

        Cheque cheque = new Cheque(
                UlidGenerator.generate(),
                req.getChequeNumber(),
                req.getAmount(),
                req.getIssuer(),
                req.getIssueDate(),
                req.getDueDate(),
                LocalDateTime.now(),
                null,
                true,
                ChequeStatus.OPEN,
                bankAccount
        );

        return converter.convertToChequeDto(commandPort.save(cheque));
    }
}
