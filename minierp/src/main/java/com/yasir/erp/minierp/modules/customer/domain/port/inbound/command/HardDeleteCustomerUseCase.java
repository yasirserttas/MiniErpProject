package com.yasir.erp.minierp.modules.customer.domain.port.inbound.command;

import java.util.UUID;

public interface HardDeleteCustomerUseCase {
    void hardDeleteCustomer(UUID customerId);
}
