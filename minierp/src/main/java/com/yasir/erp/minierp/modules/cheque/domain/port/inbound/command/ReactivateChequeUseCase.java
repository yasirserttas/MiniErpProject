package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

public interface ReactivateChequeUseCase {
    ChequeDto reactivate(String chequeId);
}
