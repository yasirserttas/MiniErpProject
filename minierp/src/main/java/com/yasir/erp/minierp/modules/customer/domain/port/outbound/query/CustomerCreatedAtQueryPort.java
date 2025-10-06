package com.yasir.erp.minierp.modules.customer.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.customer.domain.model.Customer;

import java.time.LocalDateTime;
import java.util.Set;

public interface CustomerCreatedAtQueryPort {
    Set<Customer> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
