package com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;

public interface FindSupplierByTaxNumberAndActiveUseCase {
    SupplierDto findDtoByTaxNumberAndActive(String taxNumber, Boolean active);
}