package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.SoftDeleteChequeUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SoftDeleteChequeService implements SoftDeleteChequeUseCase {

    private final ChequeCommandPort chequeCommandPort;
    private final ChequeQueryPort chequeQueryPort;
    private final ChequeConverter chequeConverter;

    public SoftDeleteChequeService(ChequeCommandPort chequeCommandPort,
                                   ChequeQueryPort chequeQueryPort,
                                   ChequeConverter chequeConverter) {
        this.chequeCommandPort = chequeCommandPort;
        this.chequeQueryPort = chequeQueryPort;
        this.chequeConverter = chequeConverter;
    }

    @Override
    public ChequeDto softDelete(String chequeId) {
        Cheque existing = chequeQueryPort
                .findByIdAndActive(chequeId, true)
                .orElseThrow(() -> new ChequeNotFoundException(chequeId));

        Cheque deleted = new Cheque(
                existing.getId(),
                existing.getChequeNumber(),
                existing.getAmount(),
                existing.getIssuer(),
                existing.getIssueDate(),
                existing.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                false,
                existing.getStatus(),
                existing.getBankAccount()
        );

        return chequeConverter.convertToChequeDto(chequeCommandPort.save(deleted));
    }
}
