package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;

import java.util.Optional;

public interface ChequeQueryPort {
    Optional<Cheque> findByIdAndActive(String id, boolean active);
    Optional<Cheque> findByChequeNumberAndActive(String chequeNumber, boolean active);
}
