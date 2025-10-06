package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.modules.cheque.application.converter.ChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.MarkOverdueChequesIfNecessaryUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeDueDateQueryPort;
import com.yasir.erp.minierp.modules.cheque.infrastructure.persistence.ChequeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MarkOverdueChequesIfNecessaryService implements MarkOverdueChequesIfNecessaryUseCase {


    private final ChequeConverter chequeConverter;
    private final ChequeDueDateQueryPort chequeDueDateQueryPort;
    private final ChequeJpaRepository chequeJpaRepository;

    public MarkOverdueChequesIfNecessaryService(ChequeConverter chequeConverter,
                                                ChequeDueDateQueryPort chequeDueDateQueryPort,
                                                ChequeJpaRepository chequeJpaRepository) {
        this.chequeConverter = chequeConverter;
        this.chequeDueDateQueryPort = chequeDueDateQueryPort;
        this.chequeJpaRepository = chequeJpaRepository;
    }

    @Override
    public Set<ChequeDto> markOverdueChequesIfNecessary() {
        LocalDateTime now = LocalDateTime.now();

        Set<Cheque> dueCheques =
                chequeDueDateQueryPort.findAllByDueDateBeforeAndStatusAndActive
                        (now, ChequeStatus.OPEN, true);

        Set<Cheque> updated = dueCheques.stream()
                .map(c -> new Cheque(
                        c.getId(),
                        c.getChequeNumber(),
                        c.getAmount(),
                        c.getIssuer(),
                        c.getIssueDate(),
                        c.getDueDate(),
                        c.getCreatedAt(),
                        LocalDateTime.now(),
                        c.getActive(),
                        ChequeStatus.OVERDUE,
                        c.getBankAccount()
                ))
                .collect(Collectors.toSet());


        return chequeConverter.convertToChequeSetDto
                (Set.copyOf(chequeJpaRepository.saveAll(updated)));
    }
}
