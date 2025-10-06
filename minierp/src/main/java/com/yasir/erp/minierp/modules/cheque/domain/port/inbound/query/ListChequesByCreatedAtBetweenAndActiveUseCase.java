package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListChequesByCreatedAtBetweenAndActiveUseCase {
    Set<ChequeDto> listChequesByCreatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active);
}
