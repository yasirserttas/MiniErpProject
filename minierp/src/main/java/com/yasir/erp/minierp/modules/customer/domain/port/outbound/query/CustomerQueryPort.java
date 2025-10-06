package com.yasir.erp.minierp.modules.customer.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerQueryPort {
    Optional<Customer> findById(UUID id);
}
