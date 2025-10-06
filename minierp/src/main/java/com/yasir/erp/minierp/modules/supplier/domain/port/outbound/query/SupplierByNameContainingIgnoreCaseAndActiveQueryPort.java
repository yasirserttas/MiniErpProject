package com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import java.util.Set;

public interface SupplierByNameContainingIgnoreCaseAndActiveQueryPort {
    Set<Supplier> findAllByNameContainingIgnoreCaseAndActive(String name, Boolean active);
}