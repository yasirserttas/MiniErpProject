package com.yasir.erp.minierp.modules.customer.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;

import java.util.Set;
import java.util.UUID;

public interface ListCustomersByUserUseCase {
    Set<CustomerDto> findDtoAllByUserId(UUID userId);
}
