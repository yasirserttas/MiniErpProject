package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderCustomerQueryPort {
    Optional<Order> findByCustomerIdAndActive(UUID customerId, boolean active);
    Set<Order> findAllByCustomerIdAndActive(UUID customerId, boolean active);
}
