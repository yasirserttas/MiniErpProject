package com.yasir.erp.minierp.modules.customer.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;

import java.util.UUID;

public interface DeactivateCustomerUseCase {
    CustomerDto deactivateCustomer(UUID customerId);
}
