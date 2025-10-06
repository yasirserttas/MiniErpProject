package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.util.Set;

public interface ListChequesByStatusBankAccountAndActiveUseCase {
    Set<ChequeDto> listChequesByStatusBankAccountAndActive
            (ChequeStatus status, String bankAccountId, boolean active);
}
