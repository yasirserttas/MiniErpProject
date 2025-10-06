package com.yasir.erp.minierp.modules.customer.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;

import java.util.UUID;

public interface ActivateCustomerUseCase {
    CustomerDto activateCustomer(UUID customerId);
}
