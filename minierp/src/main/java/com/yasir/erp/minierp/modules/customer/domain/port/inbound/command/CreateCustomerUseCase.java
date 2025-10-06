package com.yasir.erp.minierp.modules.customer.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.application.dto.request.CreateCustomerDtoRequest;

public interface CreateCustomerUseCase {
    CustomerDto addCustomer(CreateCustomerDtoRequest createCustomerDtoRequest);
}
