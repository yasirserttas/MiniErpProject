package com.yasir.erp.minierp.modules.customer.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;

public interface CustomerCommandPort {
    Customer save(Customer customer);
    void delete(Customer customer);
}
