package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderCreatedAtQueryPort {
    Set<Order> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
