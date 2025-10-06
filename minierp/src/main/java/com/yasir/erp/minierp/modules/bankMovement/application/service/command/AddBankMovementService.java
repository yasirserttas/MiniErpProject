package com.yasir.erp.minierp.modules.bankMovement.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.UpdateBankAccountBalanceUseCase;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountByIdAndStatusQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.application.converter.BankMovementConverter;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.AddBankMovementUseCase;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.command.BankMovementCommandPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
public class AddBankMovementService implements AddBankMovementUseCase {

    private final BankMovementCommandPort commandPort;
    private final BankMovementConverter converter;
    private final BankAccountByIdAndStatusQueryPort bankAccountByIdAndStatusPort;
    private final UpdateBankAccountBalanceUseCase updateBankAccountBalanceUseCase;

    public AddBankMovementService(BankMovementCommandPort commandPort,
                                  BankMovementConverter converter,
                                  BankAccountByIdAndStatusQueryPort bankAccountByIdAndStatusPort,
                                  UpdateBankAccountBalanceUseCase updateBankAccountBalanceUseCase) {
        this.commandPort = commandPort;
        this.converter = converter;
        this.bankAccountByIdAndStatusPort = bankAccountByIdAndStatusPort;
        this.updateBankAccountBalanceUseCase = updateBankAccountBalanceUseCase;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankMovementDto addBankMovement(CreateBankMovementDtoRequest createBankMovementDtoRequest) {

        BankAccount bankAccount = bankAccountByIdAndStatusPort
                .findByIdAndStatus
                        (createBankMovementDtoRequest.getBankAccountId(), BankAccountStatus.ACTIVE)
                .orElseThrow(() -> new IllegalStateException
                        ("BankAccount not found or not ACTIVE: " +
                                createBankMovementDtoRequest.getBankAccountId()));

        updateBankAccountBalanceUseCase.updateBalance(
                bankAccount.getId(),
                createBankMovementDtoRequest.getAmount(),
                createBankMovementDtoRequest.getType()
        );

        BankMovement movement = new BankMovement(
                UlidGenerator.generate(),
                createBankMovementDtoRequest.getAmount(),
                createBankMovementDtoRequest.getType(),
                BankMovementStatus.ACTIVE,
                bankAccount,
                createBankMovementDtoRequest.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return converter.convertToBankMovementDto(commandPort.save(movement));
    }
}
