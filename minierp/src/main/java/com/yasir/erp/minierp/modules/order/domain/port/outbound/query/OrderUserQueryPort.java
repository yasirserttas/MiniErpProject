package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderUserQueryPort {
    Optional<Order> findByUserIdAndActive(UUID userId, boolean active);
    Set<Order> findAllByUserIdAndActive(UUID userId, boolean active);
}
