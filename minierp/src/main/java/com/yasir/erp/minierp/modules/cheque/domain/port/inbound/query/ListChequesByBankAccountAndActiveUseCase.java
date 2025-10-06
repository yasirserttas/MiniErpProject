package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;

import java.util.Set;

public interface ListChequesByBankAccountAndActiveUseCase {
    Set<ChequeDto> listChequesByBankAccountAndActive(String bankAccountId, boolean active);
}
