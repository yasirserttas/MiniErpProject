package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.AddBankMovementUseCase;
import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.MarkChequeAsCollectedUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.AlreadyPaidException;
import com.yasir.erp.minierp.modules.cheque.application.exception.InactiveEntityException;
import com.yasir.erp.minierp.modules.cheque.application.exception.InvalidStatusException;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarkChequeAsCollectedService implements MarkChequeAsCollectedUseCase {

    private final ChequeCommandPort commandPort;
    private final ChequeQueryPort chequeQueryPort;
    private final AddBankMovementUseCase addBankMovementUseCase;
    private final ChequeConverter converter;

    public MarkChequeAsCollectedService(ChequeCommandPort commandPort,
                                        ChequeQueryPort chequeQueryPort,
                                        AddBankMovementUseCase addBankMovementUseCase,
                                        ChequeConverter converter) {
        this.commandPort = commandPort;
        this.chequeQueryPort = chequeQueryPort;
        this.addBankMovementUseCase = addBankMovementUseCase;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChequeDto markAsCollected(String chequeId) {
        Cheque existing = chequeQueryPort.findByIdAndActive(chequeId, true)
                .orElseThrow(() -> new ChequeNotFoundException(chequeId));

        if (!existing.getActive()) throw new InactiveEntityException("Cheque", chequeId);
        if (existing.getStatus() == ChequeStatus.PAID) throw new AlreadyPaidException("Cheque", chequeId);
        if (existing.getStatus() != ChequeStatus.OPEN)
            throw new InvalidStatusException("Cheque must be in OPEN status to be collected.");

        addBankMovementUseCase.addBankMovement(new CreateBankMovementDtoRequest(
                existing.getBankAccount().getId(),
                existing.getAmount(),
                BankMovementType.INCOME,
                "Collected cheque: " + existing.getChequeNumber()
        ));

        Cheque updated = new Cheque(
                existing.getId(),
                existing.getChequeNumber(),
                existing.getAmount(),
                existing.getIssuer(),
                existing.getIssueDate(),
                existing.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true,
                ChequeStatus.PAID,
                existing.getBankAccount()
        );

        return converter.convertToChequeDto(commandPort.save(updated));
    }
}
