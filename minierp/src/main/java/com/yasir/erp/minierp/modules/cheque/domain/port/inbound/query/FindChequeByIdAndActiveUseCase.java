package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

public interface FindChequeByIdAndActiveUseCase {
    ChequeDto findChequeByIdAndActive(String id, boolean active);
}
