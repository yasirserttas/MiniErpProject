package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

import java.util.Set;

public interface ListChequesByActiveUseCase {
    Set<ChequeDto> listChequesByActive(boolean active);
}
