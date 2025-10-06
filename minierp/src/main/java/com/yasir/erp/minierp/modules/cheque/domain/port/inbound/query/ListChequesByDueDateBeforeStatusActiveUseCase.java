package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListChequesByDueDateBeforeStatusActiveUseCase {
    Set<ChequeDto> listChequesByDueDateBeforeStatusActive(LocalDateTime now, ChequeStatus status, boolean active);
}
