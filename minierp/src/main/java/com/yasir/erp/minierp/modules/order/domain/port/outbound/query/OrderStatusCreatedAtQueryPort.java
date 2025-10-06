package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderStatusCreatedAtQueryPort {
    Set<Order> findAllByOrderStatusAndCreatedAtBetweenAndActive(
            OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active);
}
