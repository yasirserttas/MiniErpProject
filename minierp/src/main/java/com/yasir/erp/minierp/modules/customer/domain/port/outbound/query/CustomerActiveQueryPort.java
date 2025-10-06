package com.yasir.erp.minierp.modules.customer.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CustomerActiveQueryPort {
    Optional<Customer> findByIdAndActive(UUID id, boolean active);
    Set<Customer> findAllByActive(boolean active);
}
