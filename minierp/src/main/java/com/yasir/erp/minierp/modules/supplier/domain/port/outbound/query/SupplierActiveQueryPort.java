package com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import java.util.Set;

public interface SupplierActiveQueryPort {
    Set<Supplier> findAllByActive(Boolean active);
}