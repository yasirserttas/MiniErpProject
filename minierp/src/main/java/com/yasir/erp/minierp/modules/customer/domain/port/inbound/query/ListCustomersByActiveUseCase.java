package com.yasir.erp.minierp.modules.customer.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;

import java.util.Set;

public interface ListCustomersByActiveUseCase {
    Set<CustomerDto> findDtoAllByActive(boolean active);
}
