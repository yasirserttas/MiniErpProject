package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListChequesByDueDateBetweenStatusActiveUseCase {
    Set<ChequeDto> listChequesByDueDateBetweenStatusActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active);
}
