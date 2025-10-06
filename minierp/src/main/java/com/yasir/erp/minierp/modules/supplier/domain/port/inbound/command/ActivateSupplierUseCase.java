package com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;

public interface ActivateSupplierUseCase {
    SupplierDto activateSupplier(String supplierId);
}