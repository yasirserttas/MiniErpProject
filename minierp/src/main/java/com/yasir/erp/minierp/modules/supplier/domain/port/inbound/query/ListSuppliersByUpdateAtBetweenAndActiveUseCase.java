package com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListSuppliersByUpdateAtBetweenAndActiveUseCase {
    Set<SupplierDto> findDtoAllByUpdateAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active);
}