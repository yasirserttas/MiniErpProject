package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.util.Set;

public interface ListChequesByStatusAndActiveUseCase {
    Set<ChequeDto> listChequesByStatusAndActive(ChequeStatus status, boolean active);
}
