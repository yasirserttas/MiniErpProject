package com.yasir.erp.minierp.modules.bankMovement.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.UpdateBankAccountBalanceUseCase;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.CancelBankMovementUseCase;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.command.BankMovementCommandPort;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query.BankMovementQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.application.exception.BankMovementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CancelBankMovementService implements CancelBankMovementUseCase {

    private final BankMovementCommandPort commandPort;
    private final BankMovementQueryPort movementQueryPort;
    private final UpdateBankAccountBalanceUseCase updateBankAccountBalanceUseCase;

    public CancelBankMovementService(BankMovementCommandPort commandPort,
                                     BankMovementQueryPort movementQueryPort,
                                     UpdateBankAccountBalanceUseCase updateBankAccountBalanceUseCase) {
        this.commandPort = commandPort;
        this.movementQueryPort = movementQueryPort;
        this.updateBankAccountBalanceUseCase = updateBankAccountBalanceUseCase;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancel(String movementId) {
        BankMovement bankMovement = movementQueryPort.findById(movementId)
                .orElseThrow(() -> new BankMovementNotFoundException(movementId));

        BankMovementType opposite = (bankMovement.getType() == BankMovementType.INCOME)
                ? BankMovementType.OUTCOME : BankMovementType.INCOME;


        updateBankAccountBalanceUseCase.updateBalance(
                bankMovement.getBankAccount().getId(),
                bankMovement.getAmount(),
                opposite
        );

        BankMovement cancelled = new BankMovement(
                bankMovement.getId(),
                bankMovement.getAmount(),
                bankMovement.getType(),
                BankMovementStatus.CANCELLED,
                bankMovement.getBankAccount(),
                bankMovement.getDescription(),
                bankMovement.getCreatedAt(),
                LocalDateTime.now(),
                bankMovement.getDate()
        );

        commandPort.save(cancelled);
    }
}
