package com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.application.dto.request.CreateSupplierDtoRequest;

public interface AddSupplierUseCase {
    SupplierDto addSupplier(CreateSupplierDtoRequest req);
}