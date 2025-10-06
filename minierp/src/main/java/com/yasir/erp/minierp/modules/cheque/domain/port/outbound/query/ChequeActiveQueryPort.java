package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;

import java.util.Set;

public interface ChequeActiveQueryPort {
    Set<Cheque> findAllByActive(boolean active);
}
