package com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;

import java.time.LocalDateTime;
import java.util.Set;

public interface ReceiptDateQueryPort {
    Set<Receipt> findAllByReceiptDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}