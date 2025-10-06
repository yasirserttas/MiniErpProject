package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface ChequeDueDateQueryPort {
    Set<Cheque> findAllByDueDateBeforeAndStatusAndActive
            (LocalDateTime now, ChequeStatus status, boolean active);
    Set<Cheque> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active);
}
