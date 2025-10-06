package com.yasir.erp.minierp.modules.customer.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListCustomersByCreatedAtUseCase {
    Set<CustomerDto> findDtoAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
