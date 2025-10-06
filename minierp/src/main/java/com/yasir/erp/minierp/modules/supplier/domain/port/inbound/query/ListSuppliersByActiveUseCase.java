package com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import java.util.Set;

public interface ListSuppliersByActiveUseCase {
    Set<SupplierDto> findDtoAllByActive(Boolean active);
}