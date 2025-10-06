package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;

import java.time.LocalDateTime;
import java.util.Set;

public interface ChequeCreatedAtQueryPort {
    Set<Cheque> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
