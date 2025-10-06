package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.util.Set;

public interface ChequeStatusQueryPort {
    Set<Cheque> findAllByStatusAndActive(ChequeStatus status, boolean active);
    Set<Cheque> findAllByStatusAndBankAccountIdAndActive
            (ChequeStatus status, String bankAccountId, boolean active);
}
