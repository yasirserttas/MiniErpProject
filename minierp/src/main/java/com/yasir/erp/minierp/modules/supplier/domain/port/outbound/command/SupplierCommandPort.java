package com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;

public interface SupplierCommandPort {
    Supplier save(Supplier supplier);
    void delete(Supplier supplier); // hard delete by entity
}