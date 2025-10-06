package com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import java.time.LocalDateTime;
import java.util.Set;

public interface SupplierByUpdateAtBetweenAndActiveQueryPort {
    Set<Supplier> findAllByUpdateAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active);
}