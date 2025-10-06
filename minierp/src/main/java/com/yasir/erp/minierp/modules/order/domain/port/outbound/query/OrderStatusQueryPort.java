package com.yasir.erp.minierp.modules.order.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;

import java.util.Optional;
import java.util.Set;

public interface OrderStatusQueryPort {
    Optional<Order> findByOrderStatusAndActive(OrderStatus status, boolean active);
    Set<Order> findAllByOrderStatusAndActive(OrderStatus status, boolean active);
}
