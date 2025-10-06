package com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import java.util.Optional;

public interface SupplierByIdAndActiveQueryPort {
    Optional<Supplier> findByIdAndActive(String id, Boolean active);
}