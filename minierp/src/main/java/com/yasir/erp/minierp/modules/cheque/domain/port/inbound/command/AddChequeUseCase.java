package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.application.dto.request.CreateChequeDtoRequest;

public interface AddChequeUseCase {
    ChequeDto add(CreateChequeDtoRequest createChequeDtoRequest);
}
