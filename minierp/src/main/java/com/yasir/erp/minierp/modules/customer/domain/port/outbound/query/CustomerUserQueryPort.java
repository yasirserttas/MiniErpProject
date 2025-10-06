package com.yasir.erp.minierp.modules.customer.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;

import java.util.Set;
import java.util.UUID;

public interface CustomerUserQueryPort {
    Set<Customer> findAllByUserId(UUID userId);
    Set<Customer> findAllByUserIdAndActive(UUID userId, boolean active);
}
