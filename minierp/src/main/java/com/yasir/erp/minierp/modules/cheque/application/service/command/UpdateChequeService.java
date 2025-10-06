package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.application.dto.request.UpdateChequeDtoRequest;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.UpdateChequeUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateChequeService implements UpdateChequeUseCase {

    private final ChequeCommandPort commandPort;
    private final ChequeQueryPort chequeQueryPort;
    private final BankAccountByIdAndStatusQueryPort bankAccountByIdAndStatusPort;
    private final ChequeConverter converter;

    public UpdateChequeService(ChequeCommandPort commandPort,
                               ChequeQueryPort chequeQueryPort,
                               BankAccountByIdAndStatusQueryPort bankAccountByIdAndStatusPort,
                               ChequeConverter converter) {
        this.commandPort = commandPort;
        this.chequeQueryPort = chequeQueryPort;
        this.bankAccountByIdAndStatusPort = bankAccountByIdAndStatusPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChequeDto update(UpdateChequeDtoRequest req) {
        Cheque existing = chequeQueryPort.findByIdAndActive(req.getId(), true)
                .orElseThrow(() -> new ChequeNotFoundException(req.getId()));

        BankAccount bankAccount = bankAccountByIdAndStatusPort
                .findByIdAndStatus(req.getBankAccountId(), BankAccountStatus.ACTIVE)
                .orElseThrow(() -> new
                        IllegalStateException("BankAccount not ACTIVE: " + req.getBankAccountId()));

        Cheque updated = new Cheque(
                existing.getId(),
                req.getChequeNumber(),
                req.getAmount(),
                req.getIssuer(),
                req.getIssueDate(),
                req.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getActive(),
                existing.getStatus(),
                bankAccount
        );

        return converter.convertToChequeDto(commandPort.save(updated));
    }
}
