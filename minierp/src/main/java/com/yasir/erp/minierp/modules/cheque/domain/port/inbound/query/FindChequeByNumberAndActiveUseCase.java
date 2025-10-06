package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

public interface FindChequeByNumberAndActiveUseCase {
    ChequeDto findChequeByNumberAndActive(String chequeNumber, boolean active);
}
