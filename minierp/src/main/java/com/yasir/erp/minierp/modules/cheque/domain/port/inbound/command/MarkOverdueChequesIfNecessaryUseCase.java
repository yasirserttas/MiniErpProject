package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

import java.util.Set;

public interface MarkOverdueChequesIfNecessaryUseCase {
    Set<ChequeDto> markOverdueChequesIfNecessary();
}
